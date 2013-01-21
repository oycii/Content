package com.oycii.content
import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_MODERATOR'])
class GameModerationController {
	def generalService
	def gameService
	
    def index = { redirect(action: list) }
	
	def list = {
		def result = gameService.getGames(params)	
		def currentCategory = generalService.getCurrentCategory(params)
		log.info "currentCategory: $currentCategory"
		def genres = generalService.genresAllStatus(currentCategory)			
		log.info "games size: ${result}"
		
		return [games: result.games, count: result.size, genres: genres]
	}

	def listNoModeration = { 
		params.status = 2
		def map = gameService.getGamesByStatus(params)
		map.put("count", map.size)
		render(view:"list", model: map)
	}	

	def listYesModeration = { 
		params.status = 1
		def map = gameService.getGamesByStatus(params)
		map.put("count", map.size)
		render(view:"list", model: map)
	}	
	
	def changeStatus(obj, id, status, text) {
		if (obj != null) {
			obj.setStatus(status)
			obj.save()
			render text
		} else {
			render "Ошибка изменения статуса!"
		}
	}
	
	def remove = {
		changeStatus(Game.get(params.id), params.id, 2, "Текущий статус: -")
	}

	def add = {
		changeStatus(Game.get(params.id), params.id, 1, "Текущий статус: +")
	}
	
	def removeGenre = {
		changeStatus(Genre.get(params.id), params.id, 2, "-")
	}

	def addGenre = {
		changeStatus(Genre.get(params.id), params.id, 1, "+")
	}

}
