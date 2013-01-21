package com.oycii.content

import org.springframework.transaction.annotation.Transactional;

@Transactional
class GeneralService {
    // boolean transactional = true
	int STATUS = 0
	
	State getCurrentState(session, params) {
		log.info "getCurrentState: $params"
		
		State state = null
		if (params?.state != null) {
			state = State.findByStateId(params?.state)
		} else if (session?.stateId != null) {
			state = State.findByStateId(session?.stateId)
		}
		
		if (state == null)
			state = State.findByStateId(State.DEFAULT_STATE_ID)
			
		session?.stateId = state.stateId 
			
		return state
	}
	
	CategoryType getCurrentCategoryType(params) {
		log.info "getCurrentCategoryType: $params"
		CategoryType currentCategoryType = null
		
		if (params.categoryTypeId == null) 
			return CategoryType.findByCategoryTypeId(CategoryType.CategoryTypeItem.MELODY.id)
		else
			return CategoryType.findByCategoryTypeId(params.categoryTypeId)
	}
	
    def getCategoryByName(category) {
		log.info "getCategoryByName: ${category}"
		
		switch (category) {
			case "game": return "games"
			case "gameModeration": return "games"
			case "song": return "Realtone"
			case "songModeration": return "Realtone"
			case "image": return "images"
			case "imageModeration": return "images"
			default: return "games"
		}
	}

	def getCurrentCategory(params) {
		log.info "getCurrentCategory: $params"
		def currentCategory = (!params.categoryId)?Category.get(params.categoryId):null
		
		if (currentCategory == null) {
			if (params.category != null)
				currentCategory = Category.findByName(getCategoryByName(params.category))
			else
				currentCategory = Category.findByName(getCategoryByName(params.controller))
		}
		
		return currentCategory
	}
	
	def genres(currentCategory) {
		def genres = Genre.findAllByCategoryAndStatus(currentCategory, STATUS)
		return genres
	}
	
	List<Genre> genres(CategoryType categoryType) {
		def genres = Genre.findAllByCategoryTypeAndStatus(categoryType, STATUS)
		return genres
	}
	
	def genresAllStatus(currentCategory) {
		def genres = Genre.findAllByCategory(currentCategory)
		return genres
	}
		
	def getCode(code, id) {
		log.info "Start getCode, code: ${id}"
		def buff = ""
		def s = id.toString().size()
		if (s < 4) {
			int pos = 4 - s
			
			for (x in 0..pos - 1) {
				buff += "0"
			}
			
			buff += id.toString()
		} else {
			buff = id.toString()
		}
	
		return code.replaceAll("xxxx", buff)
	}	
}
