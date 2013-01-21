package com.oycii.content

import org.springframework.transaction.annotation.Transactional;

import groovy.util.Node;
import com.oycii.content.image.*;

@Transactional
class UpdateImagesService {
	GeneralDaoService generalDaoService
	GeneralUpdateService generalUpdateService		
	
	/** Сохранить иконки игр */
	private saveScreenshortsOfImage(Node catalog, Image image, int x) {
		log.info 'screenshort size: ' + catalog.items.item[x].screenshots.screenshot.size()
		
		for (s in 0..(catalog.items.item[x].screenshots.screenshot.size() - 1)) {
			def defaultid = (catalog.items.item.screenshots.screenshot[s].@'default')?
			catalog.items.item[x].screenshots.screenshot[s].@'default':0
			def small = (catalog.items.item[x].screenshots.screenshot[s].@small)?
					catalog.items.item[x].screenshots.screenshot[s].@small:0
			def url = catalog.items.item[x].screenshots.screenshot[s].@url
					
			def newScreenshot = new Screenshot(defaultid: defaultid, 
				url: url,
				small: small,
				image: image
			)
			
			def screenshot = generalDaoService.addObject(
				newScreenshot,
				Screenshot.findByDefaultidAndUrlAndSmallAndImage(defaultid, url, small, image),
				"Error save screenshort"
			)
			
			log.info 'screenshot: ' + screenshot.id
		}
	}
	
	/** Загрузить картинки */
	private void saveImages(Node catalog, Category category, int type) {
		log.info 'Start saveImages size: ' + catalog.items.item.size()
		for (x in 0..(catalog.items.item.size() - 1)) {
			log.info 'x: ' + x + ', typeid: ' + catalog.items.item[x].@id

			Genre genre = Genre.findByTypeidAndCategory(
				catalog.items.item[x].@genre_id, 
				category,
				[cache: true])
			log.info "genre: ${genre}"
			
			FormatInstance formatInstance = FormatInstance.findByTypeid(
				catalog.items.item[x].@fi_ids, [cache: true])
			log.info "formatInstance: ${formatInstance}"
			
			Image image = generalDaoService.addObject(
				new Image(
					typeid: catalog.items.item[x].@id,
					name: (type == 1)?catalog.items.item[x].@name:null,
					type: type,
					status: 0,
					category: category,
					genre: genre,
					formatInstance: formatInstance,
					state: category.state
				),
				Image.findByTypeid(catalog.items.item[x].@id, [cache: true]),
				'Invalid or empty image'
			)
						
			log.info "image: ${image}"
									
			// Сохранить иконки игр
			saveScreenshortsOfImage(catalog, image, x)
		}
	}	
	
	def upload(Node catalog, int type = 0) {
		// Загрузим общие данные
		Category category = generalUpdateService.saveGeneralData(catalog)
		
		// Загрузим игры
		saveImages(catalog, category, type)
		log.info "Added Images"
	}
	
	/** Разобрать по полочкам 
	 * 0 - images, 1 - logo
	 * */
	def parse(String path, String pathDTD, int type) {
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
