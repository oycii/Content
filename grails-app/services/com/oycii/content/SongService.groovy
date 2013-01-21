package com.oycii.content

import com.oycii.content.song.*;

class SongService {

    boolean transactional = true
	static STATUS = 0

	def getParams(params) {
		log.info "getParams: ${params}"
		
		params.categoryName = "melody"
		params.max = Math.min(params?.max?.toInteger() ?: 20, 100)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "name"
		params.order = params?.order ?: "asc"
		
		return params		
	}
	
    def getSongs(state, genre, params) {
		log.info "Start getSongs"
		params = getParams(params)
		log.info "params: ${params}"

   		def c = Song.createCriteria()
		def result = [:]				
    	if (genre != null) {
				if (params.phoneid == null) {
					result.songs = c {
						eq ("state", state)
						eq ("genre", genre)
						eq ("status", STATUS)
						maxResults(params.max)
						firstResult(params.offset)
						[cache: true]
					}
			
					result.size = Song.executeQuery("\
						select count(s) \
						from Song as s \
						where s.state = :state \
						and s.genre = :genre \
						and s.status = :status",
						[state: state, status: STATUS, genre: genre,
						cache: true])[0]
				} else {
					log.info "params: ${params}"
					result.songs = Song.executeQuery("\
						select s \
						from Song as s \
						left outer join s.formatInstances as fi \
						inner join fi.mediaFormat as mf \
						left outer join mf.phones as p \
						where s.state = :state \
						and p.id = :phoneid \
						and s.status = :status \
						and s.genre = :genre \
						order by s.name ", 
						[state: state, phoneid: new Long(params.phoneid), status: STATUS, 
						 genre: genre, max:params.max, offset:params.offset,
						 cache: true])

					result.size = Song.executeQuery("\
						select count(s) \
						from Song as s \
						left outer join s.formatInstances as fi \
						inner join fi.mediaFormat as mf \
						left outer join mf.phones as p \
						where s.state = :state \
						and p.id = :phoneid \
						and s.status = :status \
						and s.genre = :genre", 
						[state: state, phoneid: new Long(params.phoneid), status: STATUS, 
							genre: genre, cache: true])[0]
				}
		} else {
			if (params.phoneid == null) {
				log.info "getSongs phoneid and genre is nulls "
				result.songs = c {
					eq ("state", state)
					eq ("status", STATUS)
					maxResults(params.max)
					firstResult(params.offset)
					[cache: true]
    			}
			
				result.size = Song.executeQuery("\
					select count(s) \
					from Song as s \
					where s.state = :state \
					and s.status = :status",
					[state: state, status: STATUS, cache: true])[0]			
			} else {
				result.songs = Song.executeQuery("\
					select s \
					from Song as s \
					left outer join s.formatInstances as fi \
					inner join fi.mediaFormat as mf \
					left outer join mf.phones as p \
					where s.state = :state \
					and p.id = :phoneid \
					and s.status = :status \
					order by s.name ", 
					[state: state, phoneid: new Long(params.phoneid), status: STATUS,
					max: params.max, offset: params.offset, cache: true])

				result.size = Song.executeQuery("\
					select count(s) \
					from Song as s \
					left outer join s.formatInstances as fi \
					inner join fi.mediaFormat as mf \
					left outer join mf.phones as p \
					where s.state = :state \
					and p.id = :phoneid \
					and s.status = :status", 
					[state: state, phoneid: new Long(params.phoneid), status: STATUS,
					cache: true])[0]
			}
		}
		
		return result
    }
	
	def getPhoneSongs(state, params) {
		params = getParams(params)
		log.info params
		
		def result = [:]
		result.songs = Song.executeQuery("\
			select s \
			from Song as s \
			left outer join s.formatInstances as fi \
			inner join fi.mediaFormat as mf \
			left outer join mf.phones as p \
			where s.state = :state \
			and p.id = :phoneid \
			and s.status = :status \
			order by s.name ", 
			[state: state, phoneid: new Long(params.phoneid), status: STATUS, max:params.max, 
			 offset:params.offset, cache: true])

		result.size = Song.executeQuery("\
			select count(s) \
			from Song as s \
			left outer join s.formatInstances as fi \
			inner join fi.mediaFormat as mf \
			left outer join mf.phones as p \
			where s.state = :state \
			  and p.id = :phoneid \
			  and s.status = :status",
			[state: state, phoneid: new Long(params.phoneid), status: STATUS, cache: true])[0]
		
		return result
	}
	
	def getSingerSongs(state, singer, params) {
		def result = [:]
		def c = Song.createCriteria()
		result.songs = c {
			eq ("state", state)
			eq ("singer", singer)
			eq ("status", STATUS)
			maxResults(params.max)
			firstResult(params.offset)
			[cache: true]
		}
		
		result.size = Song.executeQuery("\
			select count(s) \
			from Song as s \
			where s.state = :state \
			and s.singer = :singer \
			and s.status = :status",
			[state: state, singer: singer, status: STATUS, cache: true])[0]
		
		return result 
	}
	
	def getSongPhones(state, songid) {
		log.info "Start getSongPhones, songid: $songid"
		
		def phones = Phone.executeQuery("\
			select p \
			from Song as s \
			left outer join s.formatInstances as fi \
			inner join fi.mediaFormat as mf \
			left outer join mf.phones as p \
			where s.state = :state \
			  and s.id = :songid ", 
			[state: state, songid: songid, cache: true]) 

		return phones
	}
	
	def getSongsByStatus(state, params) {
		params = getParams(params)
		
		def result = [:]
		result.songs = Song.createCriteria().list{
			eq('state', state)
			eq('status', params.status)
			maxResults(params.max)
    		firstResult(params.offset)
    		[cache: true]
		}
		
		result.size = Song.executeQuery("\
			select count(s) \
			from Song as s \
			where s.state = :state \
			  and s.status = :status",
			[state: state, status: STATUS, cache: true])[0]
		
		return result
	}
	
	def getSongs(state, params) {
		params = getParams(params)	

		def result = [:]
		result.songs = Song.createCriteria().list{
			eq('state', state)
			maxResults(params.max)
    		firstResult(params.offset)
    		[cache: true]
		}

		result.size = Song.executeQuery("\
			select count(s) \
			from Song as s \
			where s.state = :state",
			[state: state, cache: true])[0]
		
		return result						
	}		
	
}
