package com.oycii.content

import com.oycii.content.image.Image;
import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_MODERATOR'])
class ImageModerationController {
	def generalService
	def imageService
	
    def index = { redirect(action: list) }

	def list = {
		def result = imageService.getImages(params)	
		def currentCategory = generalService.getCurrentCategory(params)
		log.info "currentCategory: $currentCategory"
		def genres = generalService.genresAllStatus(currentCategory)			
		log.info "games size: ${result}"
		
		return [images: result.images, size: result.size, genres: genres]
	}

	def listNoModeration = {
		params.status = 2
		def map = imageService.getImagesByStatus(params)
		render(view:"list", model: map)
	}	

	def listYesModeration = { 
		params.status = 1
		def map = imageService.getImagesByStatus(params)
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
		changeStatus(Image.get(params.id), params.id, 2, "Текущий статус: -")
	}

	def add = {
		log.info "Add $params.id"
		changeStatus(Image.get(params.id), params.id, 1, "Текущий статус: +")
	}
	
	def removeGenre = {
		changeStatus(Genre.get(params.id), params.id, 2, "-")
	}

	def addGenre = {
		changeStatus(Genre.get(params.id), params.id, 1, "+")
	}
}
