package com.oycii.content

import com.oycii.content.image.*;

class ImageService {
    boolean transactional = true
	static STATUS = 0

	def getParams(params) {
		log.info "getParams: ${params}"
		
		params.categoryName = "game"
		params.max = Math.min(params?.max?.toInteger() ?: 30, 100)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "name"
		params.order = params?.order ?: "asc"
		params.genreId = params?.genreId ?params?.genreId: null
		
		log.info "getParams: ${params}"
		
		return params		
	}

    def getImages(genre, params) {
		log.info "Start getGames"
		params = getParams(params)

   		def c = Image.createCriteria()
		def result = [:]
    	if (genre != null) {
			result.images = c {
				eq ("genre", genre)
				eq ("status", STATUS)
				maxResults(params.max)
				firstResult(params.offset)
				[cache: true]
			}
			
			result.size = Image.executeQuery("\
				select count(i) \
				from Image as i \
				where i.genre = :genre \
				and i.status = :status",
				[status: STATUS, genre: genre, cache: true])[0]			
		} else {
			result.images = c {
				eq ("status", STATUS)
				maxResults(params.max)
				firstResult(params.offset)
				[cache: true]
			}
		
			result.size = Image.executeQuery("\
				select count(i) \
				from Image as i \
				where i.status = :status",
				[status: STATUS, cache: true])[0]
		}
		
		return result
    }
	
	def getImages(params) {
		params = getParams(params)	

		def result = [:]
		result.images = Image.createCriteria().list{
			maxResults(params.max)
    		firstResult(params.offset)
    		[cache: true]
		}

		result.size = Image.executeQuery("\
			select count(i) \
			from Image as i",
			[cache: true])[0]

		return result						
	}
	
	def getImagesByStatus(params) {
		params = getParams(params)
		
		def result = [:]
		result.images = Image.createCriteria().list{
			eq('status', params.status)
			maxResults(params.max)
    		firstResult(params.offset)
    		[cache: true]
		}
		
		result.size = Image.executeQuery("\
			select count(i) \
			from Image as i \
			where i.status = :status",
			[status: STATUS, cache: true])[0]		
		
		return result
	}	
	
	def getScreenshots(image) {
		log.info "Start getScreenshot: ${image}"
		
		return Screenshot.executeQuery("\
			select s \
			from Screenshot as s \
			where s.image = :image ", 
			[image: image],
			[cache: true])		
	}

}
