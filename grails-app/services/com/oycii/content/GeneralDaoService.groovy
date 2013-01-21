package com.oycii.content

import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.log.Log;

/** Ислючение доступа к данным */
class GeneralDaoException extends RuntimeException {
	String message
	def obj
}

/** Сервис для сохранения данных с учетом обновлений */
@Transactional
class GeneralDaoService {
    // boolean transactional = false

    /** Добавить что-либо */
	def addObject(newObject, currentObject, errormsg) {
		log.info "addObject, newObject: ${newObject}, currentObject: ${currentObject},  errormsg: ${errormsg}"
		def object = null
		//if (newObject == null) {
		//	object = currentObject
		//} else 
	
		if (currentObject == null) {
			object = newObject 
		} else {
			if (currentObject != newObject) {
				currentObject << newObject
				object = currentObject
			}  else
				return currentObject 
		}
		
		log.info "object: ${object}"
		boolean saveStatus = object.save(flush:true)
		if (!saveStatus) {
			log.error("error: ${errormsg}, obj: ${object}")
				// throw new GeneralDaoException(
				//	message: errormsg, obj: object)
			return null
		}
			
		return object
	}
	
    /** Добавить категорию */
	Category addCategory(Category newCategory) {
		Category category = null
		Category currentCategory = Category.findByName(newCategory.name)
		if (currentCategory == null) {
			category = newCategory
		} else {
			if (currentCategory != newCategory) {
				currentCategory << newCategory
				category = currentCategory 
			} else 
				return currentCategory
		}

		if (!category.save(flush:true)) // flush:true 
			throw new GeneralDaoException(
				message: 'Invalid or empty category', obj: category)
		
		return category		
	} 

    /** Добавить формат */
	FormatInstance addFormatInstance(FormatInstance newFormatInstance) {
		FormatInstance formatInstance = null
		FormatInstance currentFormatInstance = FormatInstance.findByTypeid(newFormatInstance.typeid)
		if (currentFormatInstance == null) {
			formatInstance = newFormatInstance
		} else {
			if (currentFormatInstance != newFormatInstance) {
				currentFormatInstance << newFormatInstance
				formatInstance = currentFormatInstance
			} else 
				return currentFormatInstance
		}

		if (!formatInstance.save(flush:true)) 
			throw new GeneralDaoException(
				message: 'Invalid or empty FormatInstance', obj: formatInstance)
		
		return formatInstance
	} 

	/** Добавить оператора */
	Operator addOperator(Operator newOperator) {
		Operator operator = null
		Operator currentOperator = Operator.findByName(newOperator.name)
		if (currentOperator == null) {
			operator = newOperator
		} else {
			if (currentOperator != newOperator) {
				currentOperator << newOperator
				operator = currentOperator 
			} else 
				return currentOperator
		}

		if (!operator.save(flush:true)) 
			throw new GeneralDaoException(
				message: 'Invalid or empty operator', obj: operator)
		
		return operator
	}
	
	/** Добавить оператора */
	Genre addGenre(Genre newGenre) {
		Genre genre = null
		Genre currentGenre = Genre.findByNameAndCategory(
			newGenre.name, newGenre.getCategory())
		if (currentGenre == null) {
			genre = newGenre
		} else {
			if (currentGenre != newGenre) {
				currentGenre << newGenre
				genre = currentGenre 
			} else 
				return currentGenre
		}

		if (!genre.save(flush:true)) 
			throw new GeneralDaoException(
				message: 'Invalid or empty genre', obj: genre)
		
		return genre
	}
		
	/** Добавить страну */
	State addState(State newState) {
		State state = null
		State currentState = State.findByRegion(newState.region)
		if (currentState == null) {
			state = newState
		} else {
			if (currentState != newState) {
				currentState << newState
				state = currentState 
			} else 
				return currentState
		}

		if (!state.save(flush:true)) 
			throw new GeneralDaoException(
				message: 'Invalid or empty state', obj: state)
		
		return state
	}

	/** Добавить телефон */
	Phone addPhone(Phone newPhone) {
		Phone phone = null
		Phone currentPhone = Phone.findByTypeid(newPhone.typeid)
		if (currentPhone == null) {
			phone = newPhone
		} else {
			if (currentPhone != newPhone) {
				currentPhone << newPhone
				phone = currentPhone 
			} else 
				return currentPhone
		}

		if (!phone.save(flush:true)) 
			throw new GeneralDaoException(
				message: 'Invalid or empty Phone', obj: phone)
		
		return phone
	}
			
	/** Добавить вендора */
	Vendor addVendor(Vendor newVendor) {
		Vendor vendor = null
		Vendor currentVendor = Vendor.findByName(newVendor.name)
		if (currentVendor == null) {
			vendor = newVendor
		} else {
			if (currentVendor != newVendor) {
				currentVendor << newVendor
				vendor = currentVendor 
			} else 
				return currentVendor
		}

		if (!vendor.save(flush:true)) 
			throw new GeneralDaoException(
				message: 'Invalid or empty Vendor', obj: vendor)
		
		return vendor
	}
	
	/** Добавить медиа формат */
	MediaFormat addMediaFormat(MediaFormat newMediaFormat)
	{
		MediaFormat mediaFormat = null
		
		MediaFormat currentMediaFormat = MediaFormat.findByTypeid(newMediaFormat.typeid)
		if (currentMediaFormat == null) {
			mediaFormat = newMediaFormat
		} else {
			if (currentMediaFormat != newMediaFormat) {
				currentMediaFormat << newMediaFormat
				mediaFormat = currentMediaFormat
			} else
				return currentMediaFormat
		}
		
		if (!mediaFormat.save(flush:true)) 
			throw new GeneralDaoException(
				message: 'Invalid or empty mediaFormat', obj: mediaFormat)
				
		return mediaFormat

	}
	
	/** Добавить версию обновления */
	UpdateVersion addUpdateVersion(UpdateVersion newUpdateVersion) 
	{
		UpdateVersion updateVersion = null
		UpdateVersion currentUpdateVersion = 
			UpdateVersion.findByVersiontoAndMediaFormat(
				newUpdateVersion.versionto, 
				newUpdateVersion.getMediaFormat()
			)
			
		if (currentUpdateVersion == null) {
			updateVersion = newUpdateVersion
		} else {
			currentUpdateVersion << newUpdateVersion
			updateVersion = currentUpdateVersion
		}
			
		if (!updateVersion.save(flush:true))
			throw new GeneralDaoException(message: 'Invalid or empty update', 
				obj: updateVersion)
			
		return updateVersion
	}
	
	/** Добавить сервисную группу */
	ServiceGroup addServiceGroup(ServiceGroup newServiceGroup) {
		ServiceGroup serviceGroup = null
		ServiceGroup currentServiceGroup = ServiceGroup.findByTypeid(newServiceGroup.typeid)
		if (currentServiceGroup == null) {
			serviceGroup = newServiceGroup
		} else {
			if (currentServiceGroup != newServiceGroup) {
				currentServiceGroup << newServiceGroup
				serviceGroup = currentServiceGroup
			} else
				return currentServiceGroup
		}

		if (!serviceGroup.save(flush:true))
			throw new GeneralDaoException(
				message: 'Invalid or empty serviceGroup', 
				obj: serviceGroup)
					
		return serviceGroup
	}
	
	/** Добавить сервис */
	Service addService(Service newService) {
		Service service = null
		Service currentService = Service.findByTypeidAndServiceGroup(
			newService.typeid, newService.getServiceGroup())
		if  (currentService == null) {
			service = newService
		} else {
			if (currentService != newService) {
				currentService << newService
				service = currentService 				
			} else
				return currentService 
		}
		
		if (!service.save(flush:true))
			throw new GeneralDaoException(
				message: 'Invalid or empty service', 
				obj: service)
			
		return service
	}
	
	/** Добавить игру */
	Game addGame(Game newGame) {
		Game game = null
		Game currentGame = Game.findByTypeid(newGame.typeid)
		if  (currentGame == null) {
			game = newGame
		} else {
			if (currentGame != newGame) {
				currentGame << newGame
				game = currentGame
			} else
				return currentGame
		}
		
		if (!game.save(flush:true))
			throw new GeneralDaoException(
				message: 'Invalid or empty game', 
				obj: game)
			
		return game
	}	
}
