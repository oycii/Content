package com.oycii.content

import com.oycii.content.Category;
import com.oycii.content.Game;
import com.oycii.content.Genre;

class SearchController {
	def gameService
	
    // def index = {  redirect(action: search) }
	def search = {
		log.info "Start ${params}"
    	def query = params.q
		
		if (params.phoneid == null) {
			if (session?.phoneid != null)
				params.phoneid = session.phoneid
		}
		
		def categores = Category.list()		
		
		def currentCategory = gameService.getCurrentCategory(params)
		log.info "currentCategory: ${currentCategory}"
		
		def genres = Genre.findAllByCategory(currentCategory)
		log.info "genres size: ${genres.size()}"
		
		def currentGenre = (params.genreId != null)?Genre.get(params.genreId):null
		log.info "currentGenre: ${currentGenre}"
		
		if (!query) {
			return [:]
		}
		
		try {
			log.info "Start searchResult"
			def searchResult = Game.search(query, params)
			log.info "searchResult: ${searchResult}"
			return [categores:categores, genres: genres,  searchResult: searchResult, total: searchResult.total]
		} catch(e) {
			log.info "Error searchResult"
			return [searchError: true]
		}
	}
}
