package com.oycii.content

import com.oycii.content.song.*;

class SongController {
	def scaffold = false
	SongService songService
	GeneralService generalService

    def index = { redirect(action: list) }
	
	def list = {
		log.info "genreId: ${params}"
		def phone
		if (session?.phoneid != null) {
			params.phoneid = session.phoneid 
			phone = Phone.get(session.phoneid)
		}
		
		def categores = Category.list()		
		
		songService.getParams(params)
		log.info "genreId: ${params}"
		log.info "genreId: ${generalService}"
		
		//def currentCategory = generalService.getCurrentCategory(params)
		//log.info "currentCategory: ${currentCategory}"
		State state = generalService.getCurrentState(session, params)
		
		CategoryType categoryType = generalService.getCurrentCategoryType(params)
		log.info "categoryType: ${categoryType}"
		
		def genres = generalService.genres(categoryType)		
		def currentGenre = (params.genreId != null)?
			Genre.get(params.genreId):null
			
		log.info "currentGenre: ${currentGenre}"

		def result = songService.getSongs(state, currentGenre, params)
		def count = result.size
		params.totalSongs = count

		log.info "songs size: ${count}"
		
		return [state: state, categores: categores, genres:genres, songs: result.songs, 
			count: count, categoryType: categoryType, genre: currentGenre, 
			phone: phone]
	}
	
	def singer = {
		log.info "genreId: ${params}"
		def phone
		if (session?.phoneid != null) {
			params.phoneid = session.phoneid 
			phone = Phone.get(session.phoneid)
		}
		
		def categores = Category.list()		
		songService.getParams(params)
		
		State state = generalService.getCurrentState(session, params)
		def currentCategory = generalService.getCurrentCategory(params)		
		def genres = generalService.genres(currentCategory)		
		def currentGenre = (params.genreId != null)?
			Genre.get(params.genreId):null

		def singer = Singer.get(params.singerid)
		def result = songService.getSingerSongs(state, singer, params)
		def songs = result.songs
		params.totalSongs = result.size

		log.info "songs size: ${result.size}"
		
		def map = [state: state, categores: categores, genres:genres, singer: singer, 
			songs: songs, count: result.size, category: currentCategory, 
			genre: currentGenre, phone: phone]
		
		render(view:"list", model: map)
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
		
		State state = generalService.getCurrentState(session, params)
		def currentCategory = generalService.getCurrentCategory(
			songService.getParams(params))
		def genres = Genre.findAllByCategory(currentCategory)
		def currentGenre = (params.genreId != null)?
			Genre.get(params.genreId):null
		
		def phone = Phone.get(params.phoneid)
		if (phone)
			session.phoneid = phone.id
			
		def result = songService.getPhoneSongs(state, params)
		
		params.totalSongs = result.size
		log.info "songs: ${result.songs}"
						
		return [state: state, categores: categores, genres: genres, songs: result.songs, 
			count: result.size, category: currentCategory, genre: currentGenre, 
			phone: phone]	
	}
	
	def listWithoutPhone = {
		session.phoneid = null
		redirect(action: list)
	}
	
	def buy = {
		log.info "buy: ${params}"
		
		def phone
		if (session?.phoneid != null)
				phone = Phone.get(session.phoneid)		
		
		State state = generalService.getCurrentState(session, params)
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)		
		
		def song = Song.get(params.id)
		def songPreviews = SongPreview.findAllBySong(song)
		def phones = songService.getSongPhones(song.id)
		def formatInstance = FormatInstance.get(params.formatInstanceId)
		def code = formatInstance.mediaFormat.codeMask
		 
		code =  generalService.getCode(code, song.typeid)
		def serviceGroup = formatInstance.getServiceGroup()
		def services =  Service.findAllByServiceGroup(serviceGroup)
		
		return [state: state, song: song, songPreviews: songPreviews, phones: phones, 
			formatInstance: formatInstance, serviceGroup: serviceGroup,
			services: services, genres: genres, code: code, 
			phone: phone]
	}	
}
