package com.oycii.content

class GamePhones implements Serializable {
	int gameid
	String phones
	
	String toString() {
		"${gameid} ${phones}"
	}
}
