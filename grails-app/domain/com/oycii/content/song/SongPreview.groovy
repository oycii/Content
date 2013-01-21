package com.oycii.content.song

import java.io.Serializable;
import java.util.Date;

class SongPreview implements Serializable {
	String url
	int status
	Date dateCreated
	Date lastUpdated
	
	Song song
	SongPreviewFormat songPreviewFormat
		
    static constraints = {
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof SongPreview)) return false
		if (url != other.url) return false
				
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof SongPreview)) 
			throw new IllegalArgumentException('Put object is not SongPreview')

		this.url = other.url			
		
		return this
	}
	
	String toString() {
		"${url} ${status}"
	}	
}
