package com.oycii.content

import com.oycii.content.State;

class TestPutAt {
	
	static void main (args) {
		State rState = new State(name: 'test r', region: 'ru r', status: 0)
		State lState = new State(name: 'test l', region: 'ru l', status: 1)
		
		
		println "rState: ${rState}"
		println "lState: ${lState}"
		
		rState << lState
		
		println "rState: ${rState}"
		println "lState: ${lState}"
		
		//rState = 10
		//println "rState: ${rState}"
	}
}
