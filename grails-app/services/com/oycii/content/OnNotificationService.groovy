package com.oycii.content

class OnNotificationService {
	static expose = ['jms']
	static destination = "queue.notification"
	static listenerCount = 10
	boolean transactional = false
	
	PhoneService phoneService
		
	def onMessage(messageObject) {
		log.info "GOT MESSAGE: $messageObject"
		phoneService.savePhonesOfGame(messageObject)
    }
}
