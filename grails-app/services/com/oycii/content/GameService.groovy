package com.oycii.content

class GameService {
    boolean transactional = true
	static STATUS = 0

	def getParams(params) {
		log.info "getParams: ${params}"
		
		params.categoryName = "game"
		params.max = Math.min(params?.max?.toInteger() ?: 9, 100)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "name"
		params.order = params?.order ?: "asc"
		
		return params		
	}

    def getGames(genre, params) {
		log.info "Start getGames"
		params = getParams(params)

   		def c = Game.createCriteria()
		def result = [:]
    	if (genre != null) {
				if (params.phoneid == null) {
					result.games = c {
						eq ("genre", genre)
						eq ("status", STATUS)
						maxResults(params.max)
						firstResult(params.offset)
						[cache: true]
					}
			
					result.size = Game.executeQuery("\
						select count(g) \
						from Game as g \
						where g.genre = :genre \
						and g.status = :status",
						[status: STATUS, genre: genre,
						 cache: true])[0]
				} else {
					result.games = c {
						phones{
							eq('id', new Long(params.phoneid))
						}	
						eq ("genre", genre)
						eq ("status", STATUS)
						maxResults(params.max)
						firstResult(params.offset)
						[cache: true]
					}
					
					result.size = Game.executeQuery("\
						select count(g) \
						from Game as g \
						left outer join g.phones as p \
						where p.id = :phoneid \
						and g.status = :status \
						and g.genre = :genre", 
						[phoneid: new Long(params.phoneid), status: STATUS, 
							genre: genre, cache: true])[0]
				}
		} else {
			if (params.phoneid == null) {
				result.games = c {
					eq ("status", STATUS)
					maxResults(params.max)
					firstResult(params.offset)
    			}
			
				result.size = Game.executeQuery("\
					select count(g) \
					from Game as g \
					where g.status = :status",
					[status: STATUS, cache: true])[0]					
			} else {
				result.games = c {
					phones {
						eq('id', new Long(params.phoneid))
					}
					eq ("status", STATUS)
					maxResults(params.max)
					firstResult(params.offset)
					[cache: true]
    			}
			
				result.size = Game.executeQuery("\
					select count(g) \
					from Game as g \
					left outer join g.phones as p \
					where p.id = :phoneid \
					and g.status = :status", 
					[phoneid: new Long(params.phoneid), status: STATUS, 
					 cache: true])[0]
			}
		}
		
		return result
    }
	
	def getGamePhone(gameid, phoneid) {
		def c = Game.createCriteria()
		return c {
			eq ("id", gameid)
			phones {
				eq('id', phoneid)
			}
			[cache: true]
		}		
	}
	
	def getPhonesOfGame(gameid) {
		def p = Phone.createCriteria()
		return p {
			games {
				eq("id", gameid)
				[cache: true]
			}
		}		
	}			
	
	def getPhoneGames(params) {
		params = getParams(params)
		
		def result = [:]		
		result.games = Game.createCriteria().list{
			phones{
				eq('id', new Long(params.phoneid))
			}
			eq ("status", STATUS)
			maxResults(params.max)
    		firstResult(params.offset)
    		[cache: true]
		}
		
		result.size = Game.executeQuery("\
			select count(g) \
			from Game as g \
			left outer join g.phones as p \
			where p.id = :phoneid \
			and g.status = :status", 
			[phoneid: new Long(params.phoneid), status: STATUS, 
			 cache: true])[0]
		
		return result
	}

	def getGamesByStatus(params) {
		params = getParams(params)
		
		def result = [:]
		result.games = Game.createCriteria().list{
			eq('status', params.status)
			maxResults(params.max)
    		firstResult(params.offset)
    		[cache: true]
		}
		
		result.size = Game.executeQuery("\
			select count(g) \
			from Game as g \
			where g.status = :status",
			[status: STATUS, cache: true])[0]					
		
		return result
	}
	
	def getGames(params) {
		params = getParams(params)	

		def result = [:]
		result.games = Game.createCriteria().list{
			maxResults(params.max)
    		firstResult(params.offset)
    		[cache: true]
		}

		result.size = Game.executeQuery("\
			select count(g) \
			from Game as g",
			[cache: true])[0]
				
		return result						
	}	
}
