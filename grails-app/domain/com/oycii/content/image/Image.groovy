package com.oycii.content.image

import java.io.Serializable;
import java.util.Date;

import com.oycii.content.Category;
import com.oycii.content.FormatInstance;
import com.oycii.content.Genre;
import com.oycii.content.Screenshot;
import com.oycii.content.State

class Image implements Serializable {
	static searchable = {
		only = ['name']
	}
	
	int typeid
	String name
	int type // 0 - image, 1 - logo
	int status
	Date dateCreated
	Date lastUpdated
	
	Category category
	Genre genre
	FormatInstance formatInstance
	State state
	
	static hasMany = [screenshots: Screenshot]
			
    static constraints = {
		typeid(blank:false, unique: true, index: 'game_typeid_idx')
		name(nullable: true)
		category(nullable: true)
		genre(nullable: true)
		formatInstance(nullable: true)		
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Image)) return false
		if (typeid != other.typeid) return false
		if (category != other.category) return false
		if (genre != other.genre) return false
		if (formatInstance != other.formatInstance) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Image)) 
			throw new IllegalArgumentException('Put object is not Image')

		this.typeid = other.typeid			
		this.category = other.category
		this.genre = other.genre
		this.formatInstance = other.formatInstance
		
		return this
	}
	
	String toString() {
		"${typeid} ${category} ${genre} ${formatInstance}"
	}		
	
}
