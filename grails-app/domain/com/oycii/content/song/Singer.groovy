package com.oycii.content.song

import java.io.Serializable;
import java.util.Date;

class Singer implements Serializable {
	String name
	int status
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [songs: Song]
    
	static constraints = {
		name(blank:false, unique: true, index: 'singer_name_idx')
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Singer)) return false
		if (name != other.name) return false
				
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Singer)) 
			throw new IllegalArgumentException('Put object is not Singer')

		this.name = other.name			
		
		return this
	}
	
	String toString() {
		"${name} ${status}"
	}	
}
