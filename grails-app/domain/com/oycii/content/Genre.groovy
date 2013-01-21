package com.oycii.content


import com.oycii.content.song.*;
import com.oycii.content.image.*;

import java.io.Serializable;

class Genre implements Serializable { 
	int typeid
	String name
	int status	
	Date dateCreated
	Date lastUpdated
	
	Category category
	CategoryType categoryType
	
	static hasMany = [games: Game, songs: Song, imags: Image]

    static constraints = {
		name(blank:false, index: 'genre_name_idx')
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Genre)) return false
		if (typeid != other.typeid) return false
		if (name != other.name) return false
		if (category != other.category) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Genre)) 
			throw new IllegalArgumentException('Put object is not Genre')

		this.typeid = other.typeid			
		this.name = other.name
		this.category = other.category
		
		return this
	}
	
	String toString() {
		"${typeid} ${name} ${category}"
	}
}
