package com.oycii.content.song

import java.io.Serializable;
import java.util.Date;

class SongPreviewFormat implements Serializable {
	String name
	int status
	Date dateCreated
	Date lastUpdated
		
	static hasMany = [songPreviews: SongPreview]	
		
    static constraints = {
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof SongPreviewFormat)) return false
		if (name != other.name) return false
				
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof SongPreviewFormat)) 
			throw new IllegalArgumentException('Put object is not SongPreviewFormat')

		this.name = other.name			
		
		return this
	}
	
	String toString() {
		"${name} ${status}"
	}	
}
