package com.oycii.content

import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional;

/** Ислючение обнавления игр */
class UpdateGamesException extends RuntimeException {
	String message
	def obj
}

@Transactional
class UpdateGamesService {
	def dataSource
	def sessionFactory
	def generalDaoService
	def generalUpdateService
	def gameService	
	
	/** Запустить скрипт сохранения телефонов игры */
	private executeSavePhonesOfGame(sqlString) {
		log.info "Start executeSavePhonesOfGame: ${sqlString}"
		Sql sql = new Sql(dataSource)
		sql.execute(sqlString)
	}
	
	/** Получить телефоны игры */
	def getPhonesOfGame(gameid) {
		def phones = gameService.getPhonesOfGame(gameid)
		def array = []
		
		for (p in 0..(phones.size() - 1)) {
			array << phones[p].id
		}
		
		return array
	}
	
	/** Сохранить телефоны игры */
	private savePhonesOfGame(HashMap mapPhones, Node catalog, Game game, int x, boolean isUpdate) {
		print "mapPhones: ${mapPhones.size()}"
		def phones = catalog.items.item[x].@supported_phones.split(',')
		log.info 'phones: ' + phones.size()
		
		if (phones.size() == 0)
			return
		
		log.info "phone size: ${phones.size()}"
		def gamePhones
		if (isUpdate == true) {
			gamePhones = getPhonesOfGame(game.id)
		}
		
		def is = false
		def inserts = "insert into phone_games (game_id, phone_id) values "
		for (p in 0..(phones.size() - 1)) {
			int phoneId = phones[p].toInteger() 
			Phone phone = mapPhones[phoneId] // Phone.findByTypeid(phones[p], [cache: true])
			log.info "p: ${phoneId} phone: ${phone}"
			if (phone == null) {
				phone = Phone.findByTypeid(phones[p], [cache: true])
			}
			log.info "phone ${phone}"
			if (phone != null) {
					def isAdd = false
					if (isUpdate == true) {
	 					if (gamePhones.contains(phone.id) == false)
							isAdd = true
					} else {
						isAdd = true
					}
					
					if (isAdd == true) {
						if (is == true)
							inserts += ","

						inserts += "(${game.id}, ${phone.id})"
						is = true						
					}
					// game.addToPhones(phone)
			}		
		}
		
		if (is == true) {
			inserts += ";"
			executeSavePhonesOfGame(inserts)
		}
		
		log.info "Added game: ${game.id}, phones: ${phones.size()}"
		// game.save(flush:true) // 
	}
	
	/** Сохранить иконки игр */
	private saveScreenshortsOfGame(Node catalog, Game game, int x) {
		def count = catalog.items.item[x].screenshots.screenshot.size()
		log.info "screenshort size: ${count}"
		
		if (count == 0)
			return
		
		for (s in 0..(count - 1)) {
			def defaultid = (catalog.items.item[x].screenshots.screenshot[s].@'default')?
			catalog.items.item[x].screenshots.screenshot[s].@'default':0
			def small = (catalog.items.item[x].screenshots.screenshot[s].@small)?
					catalog.items.item[x].screenshots.screenshot[s].@small:0
					
			def screenshot = new Screenshot(defaultid: defaultid, 
				url: catalog.items.item[x].screenshots.screenshot[s].@url,
				small: small,
				game: game
			)
			
			screenshot.save()
			
			log.info 'screenshot: ' + screenshot.id
			// game.addToScreenshots(screenshot)
		}
	}
	
	/** Загрузить игры */
	private void saveGames(Node catalog, Category category) {
		// Получим все телефоны
		List phoneList = Phone.list()
		
		HashMap mapPhones = [:]
		phoneList.each{ it -> mapPhones[it.typeid] = it}
		
		log.info 'Start saveGames games size: ' + catalog.items.item.size()
		for (x in 0..(catalog.items.item.size() - 1)) {
			log.info 'x: ' + x + ', typeid: ' + catalog.items.item[x].@id + 
				', name: ' + catalog.items.item[x].@name +
				', description: ' + (catalog.items.item[x].description[0] == null)?'':catalog.items.item[x].description[0].text()

			Genre genre = Genre.findByTypeidAndCategory(
				catalog.items.item[x].@genre_id,
				category, 
				[cache: true])
			log.info "genre: ${genre}"
			FormatInstance formatInstance = 
				FormatInstance.findByTypeid(catalog.items.item[x].@fi_ids, [cache: true])
			log.info "formatInstance: ${formatInstance}"
			
			def currentGame = Game.findByTypeid(catalog.items.item[x].@id, [cache: true])
			
			Game game = generalDaoService.addObject(
				new Game(
					typeid: catalog.items.item[x].@id,
					name: catalog.items.item[x].@name,
					description: (catalog.items.item[x].description[0] == null)?null:catalog.items.item[x].description[0].text(),
					status: 0,
					category: category,
					genre: genre,
					formatInstance: formatInstance,
					state: category.state
				),
				currentGame,
				'Invalid or empty game'
			)
						
			log.info "game: ${game}"
			
			// Сохранить телефоны игры
			if (catalog.items.item[x].@supported_phones != null) {
				// def phones = catalog.items.item[x].@supported_phones.split(',')
				if (currentGame == null) {
					log.info "Start save new game: ${game.id}"
					savePhonesOfGame(mapPhones, catalog, game, x, false)
				} else {
					log.info "Start update game: ${game.id}"
					savePhonesOfGame(mapPhones, catalog, game, x, true)
				}
			}
						
			// Сохранить иконки игр
			saveScreenshortsOfGame(catalog, game, x)
		}
	}	
	
	def upload(Node catalog, int type = 0) {
		// Загрузим общие данные
		Category category = generalUpdateService.saveGeneralData(catalog)
		log.info "category: ${category}"
		
		// Загрузить принадлежность Медиа форматов моделям телефонов
		if (type == 1) {
			generalUpdateService.savePhonesOfMediaFormats(catalog)
		}
		
		// Загрузим игры
		saveGames(catalog, category)
		log.info "Added Games"
	}	
	
	/** Разобрать по полочкам 
	 * type: 0 - всё, 1 - Video */
	def parse(String path, String pathDTD, int type = 0) {
		log.info "Start parse"
		def entityResolver = { a, b ->
			new org.xml.sax.InputSource(new File(pathDTD).newInputStream())
		} as org.xml.sax.EntityResolver

		Node catalog = 
			new XmlParser(entityResolver: entityResolver).parse(new File(path))

		log.info "complite parse file: ${path}"
		
		upload(catalog, type)
	}
}
