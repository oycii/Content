package com.oycii.content.song

import java.io.Serializable;
import java.util.Date;

import com.oycii.content.Category;
import com.oycii.content.FormatInstance;
import com.oycii.content.Genre;
import com.oycii.content.State;

class Song implements Serializable {
	static searchable = {
		only = ['name', 'singer']
	}
	
	int typeid
	String name
	int status
	Date dateCreated
	Date lastUpdated
	
	Singer singer
	Category category
	Genre genre
	State state

	static belongsTo = FormatInstance
	static hasMany = [songPreviews: SongPreview, formatInstances: FormatInstance]
	
    static constraints = {
		typeid(blank:false, unique: true, index: 'song_typeid_idx')
		name(blank:false, index: 'song_name_idx')
		singer(nullable: true)
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Song)) return false
		if (typeid != other.typeid) return false
		if (name != other.name) return false
				
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Song)) 
			throw new IllegalArgumentException('Put object is not Song')

		this.typeid = other.typeid
		this.name = other.name
		
		return this
	}
	
	String toString() {
		"${typeid} ${name} ${singer}"
	}
}
