package com.oycii.content

import com.oycii.content.GamePhones;

class NotificationController {

    def index = { 
		log.info "Start notification"
		def message = "Hi, this is a Hello World with JMS & ActiveMQ, " + new Date()
		def gamePhones = new GamePhones(gameid: 1, phones: "1,2,3,4") 
		sendJMSMessage("queue.notification", gamePhones)
		render message	
	}
}
