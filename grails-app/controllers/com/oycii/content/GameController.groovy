package com.oycii.content

import com.oycii.content.Category;
import com.oycii.content.Game;
import com.oycii.content.Genre;
import com.oycii.content.Phone;
import com.oycii.content.Screenshot;
import com.oycii.content.Service;
import com.oycii.content.Vendor;

class GameController {
	def scaffold = false
	def gameService
	def generalService
	
    def index = { redirect(action: list) }
	
	def list = {
		log.info "genreId: ${params}"
		def phone
		if (session?.phoneid != null) {
			params.phoneid = session.phoneid 
			phone = Phone.get(session.phoneid)
		}
		
		def categores = Category.list()		
		
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)
		log.info "genres size: ${genres.size()}"
		def currentGenre = (params.genreId != null)?
			Genre.get(params.genreId):null
		log.info "currentGenre: ${currentGenre}"

		def result = gameService.getGames(currentGenre, params)
		log.info "result: ${result}"
		def games = result.games
		params.totalGames = result.size
		
		return [categores: categores, genres:genres, games: games, 
			c: result.size, category: currentCategory, genre: currentGenre, 
			phone: phone]
	}
	
	def listWithoutPhone = {
		session.phoneid = null
		redirect(action: list)
	}
	
	def show = {
		log.info "genreId: ${params}"
		def game = Game.get(params.id)
		def screenshots = Screenshot.findAllByGame(game)
		
		log.info "screenshorts: ${screenshots}"
		
		return [game: game, screenshots:screenshots]
	}
	
	def gamephones = {
		log.info "genreId: ${params}"
		def game = Game.get(params.gameid)
		def screenshots = Screenshot.findAllByGame(game)
		
		return [game: game, phones: game.phones, screenshots:screenshots]
	}	
	
	def buy = {
		log.info "genreId: ${params}"
		
		def phone
		if (session?.phoneid != null)
				phone = Phone.get(session.phoneid)		
		
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)		
		
		def game = Game.get(params.id)
		def screenshots = Screenshot.findAllByGame(game)
		def phones = game.phones
		def formatInstance = game.getFormatInstance()
		def code = formatInstance.mediaFormat.codeMask
		 
		code =  generalService.getCode(code, game.typeid)
		def serviceGroup = formatInstance.getServiceGroup()
		def services =  Service.findAllByServiceGroup(serviceGroup)
		
		return [game: game, screenshots:screenshots, phones: phones, 
			formatInstance: formatInstance, serviceGroup: serviceGroup,
			services: services, genres: genres, code: code, 
			phone: phone]		
	}
	
	def phonecontent = {
		log.info "genreId: ${params}"
		
		if (params.phoneid == null) {
			if (session?.phoneid != null)
				params.phoneid = session.phoneid
			else
				redirect(action: list)
		}
		
		def categores = Category.list()		
		
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)
		log.info "genres size: ${genres.size()}"
		
		def currentGenre = (params.genreId != null)?
			Genre.get(params.genreId):null
		log.info "currentGenre: ${currentGenre}"
		
		def phone = Phone.get(params.phoneid)
		if (phone)
			session.phoneid = phone.id
			
		def result = gameService.getPhoneGames(params)
		
		params.totalGames = result.size
		log.info "games: ${result.size}"
						
		return [categores: categores, genres:genres, games: result.games, 
			c: result.size, category: currentCategory, genre: currentGenre, 
			phone: phone]
	}
}
