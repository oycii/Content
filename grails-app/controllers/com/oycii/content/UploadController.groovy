package com.oycii.content

import grails.plugins.springsecurity.Secured;

// @Secured(['ROLE_ADMIN'])
class UploadController {
	def updateGamesService
	def updateSongsService
	def updateImagesService

    def index = {

	}
	
	def update = {
		log.info "Start update, type: ${params.type}"
		def map = [:]

		switch (params.type) {
			case "games": log.info "games"
				map = [path: "/opt/oycii/projects/Partner/Content/web-app/data/RU/Games/0/0.xml", 
					pathDTD: "/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd", 
					type: 0]
				render(view:"games", model: map)
				break;
			case "songs": 
				map = [path: "/opt/oycii/projects/Partner/Content/web-app/data/RU/Melody/0/0.xml", 
					pathDTD: "/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd"]
				render(view:"songs", model: map)
				break;
			case "images": 
				map = [path: "/opt/oycii/projects/Partner/Content/web-app/data/RU/Img/0/0.xml", 
					pathDTD: "/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd",
					type: 0]
				render(view:"images", model: map)
				break;
			default: redirect(action: index) 
		}
	}
	
	def uploadGames = {
		log.info "Start uploadGames"
		updateGamesService.parse(params.path, params.pathDTD, new Integer(params.type))
		redirect(action: index) 	
	}
	
	def uploadSongs = {
		log.info "Start updateSongs"
		updateSongsService.parse(params.path, params.pathDTD)
		redirect(action: index) 
	}

	def uploadImages = {
		log.info "Start updateImages"
		updateImagesService.parse(params.path, params.pathDTD, new Integer(params.type))
		redirect(action: index)
	}
}
