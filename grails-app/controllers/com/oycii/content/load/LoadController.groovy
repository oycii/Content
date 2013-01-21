package com.oycii.content.load

class LoadController {
	def loadService

    def index() { }
	
	def start() { 
		// Загрузим данные
		loadService.load()
	}
}
