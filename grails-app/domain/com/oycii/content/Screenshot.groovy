package com.oycii.content

import java.io.Serializable;

import com.oycii.content.image.*;

class Screenshot implements Serializable {
	int defaultid
	String url 
	int small
	Date dateCreated
	Date lastUpdated		
	
	Game game
	Image image

    static constraints = {
		game(nullable: true)
		image(nullable: true)
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Screenshot)) return false
		if (defaultid != other.defaultid) return false
		if (url != other.url) return false
		if (small != other.small) return false
		if (game != other.game) return false
		if (image != other.image) return false		
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Screenshot)) 
			throw new IllegalArgumentException('Put object is not Screenshot')

		this.defaultid = other.defaultid			
		this.url = other.url
		this.small = other.small
		this.game = other.game
		this.image = other.image
		
		return this
	}
	
	String toString() {
		"${defaultid} ${url} ${small}"
	}		
	
}
