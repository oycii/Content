package com.oycii.content

import com.oycii.content.image.*;

class ImageController {
	def scaffold = false
	def imageService
	def generalService
		
    def index = { redirect(action: list) }
	
	def list = {
		log.info "genreId: ${params}"
		
		def phone
		if (session?.phoneid != null) {
			params.phoneid = session.phoneid 
			phone = Phone.get(session.phoneid)
		}
		
		def genreid
		if (session?.genreid != null) {
			params.genreid = session.genreid
		}		
		
		def categores = Category.list()		
		
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)
		log.info "genres size: ${genres.size()}"
		def currentGenre = (params.genreId != null)?Genre.get(params.genreId):null
		log.info "currentGenre: ${currentGenre}"

		def result = imageService.getImages(currentGenre, params)
		def images = result.images
		params.total = result.size
		
		log.info "images: ${images}"
		log.info "images size: ${result.size}"
		
		return [categores: categores, genres:genres, images: images, c: result.size, 
			category: currentCategory, genre: currentGenre, phone: phone]
	}
	
	def buy = {
		log.info "genreId: ${params}"
		
		def phone
		if (session?.phoneid != null)
				phone = Phone.get(session.phoneid)		
		
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)		
		
		def image = Image.get(params.id)
		def screenshots = imageService.getScreenshots(image)
		def formatInstance = image.getFormatInstance()
		def code = formatInstance.mediaFormat.codeMask
		 
		code =  generalService.getCode(code, image.typeid)
		def serviceGroup = formatInstance.getServiceGroup()
		def services =  Service.findAllByServiceGroup(serviceGroup)
		
		return [image: image, screenshots:screenshots,  
			formatInstance: formatInstance, serviceGroup: serviceGroup,
			services: services, genres: genres, code: code, 
			phone: phone]		
	}
}