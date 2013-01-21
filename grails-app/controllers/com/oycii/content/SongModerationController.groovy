package com.oycii.content

import com.oycii.content.song.*;
import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_MODERATOR'])
class SongModerationController {
	def generalService
	def songService
	
    def index = { redirect(action: list) }
	
	def list = {
		def result = songService.getSongs(params)	
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genresAllStatus(currentCategory)			
		
		return [songs: result.songs, count: result.size, genres: genres]
	}
	
	def listNoModeration = { 
		params.status = 2
		def result = songService.getSongsByStatus(params)
		def currentCategory = generalService.getCurrentCategory(params)
		result.genres = generalService.genresAllStatus(currentCategory)
		result.count = result.size		

		render(view:"list", model: result)
	}
	
	def listYesModeration = { 
		params.status = 1
		def result = songService.getSongsByStatus(params)
		def currentCategory = generalService.getCurrentCategory(params)
		result.genres = generalService.genresAllStatus(currentCategory)
		result.count = result.size			

		render(view:"list", model: result)
	}
	
	def changeStatus(obj, id, status, text) {
		log.info "changeStatus: $obj"
		if (obj != null) {
			obj.setStatus(status)
			obj.save()
			render text
		} else {
			render "Ошибка изменения статуса!"
		}
	}
	
	def remove = {
		changeStatus(Song.get(params.id), params.id, 2, "Текущий статус: -")
	}

	def add = {
		changeStatus(Song.get(params.id), params.id, 1, "Текущий статус: +")
	}
	
	def removeGenre = {
		changeStatus(Genre.get(params.id), params.id, 2, "-")
	}

	def addGenre = {
		changeStatus(Genre.get(params.id), params.id, 1, "+")
	}
	
	
}
