package com.oycii.content

class StateController {
	def scaffold = false
	GeneralService generalService

    def index() { 
		redirect(action: list)
	}
	
	def list() {
		log.print "Start state list"
		State state = generalService.getCurrentState(session, params)
		def states = State.list()
		return [state: state, states: states]
	}
	
	def choose() {
		redirect(action: list)
	}
}
