package com.oycii.content


import java.io.Serializable;

class Game implements Serializable {

	static searchable = {
		only = ['name', 'description']
	}
	
	int typeid
	String name
	String description
	int status
	Date dateCreated
	Date lastUpdated
	
	Category category
	Genre genre
	FormatInstance formatInstance
	State state
	
	static belongsTo = Phone
	static hasMany = [screenshots: Screenshot, phones: Phone]
	
    static constraints = {
		typeid(blank:false, unique: true, index: 'game_typeid_idx')
		name(blank:false)
		description(nullable: true, maxSize:5000)
		category(nullable: true)
		genre(nullable: true)
		formatInstance(nullable: true)
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Game)) return false
		if (typeid != other.typeid) return false
		if (name != other.name) return false
		if (description != other.description) return false
		if (category != other.category) return false
		if (genre != other.genre) return false
		if (formatInstance != other.formatInstance) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Game)) 
			throw new IllegalArgumentException('Put object is not Game')

		this.typeid = other.typeid			
		this.name = other.name
		this.description = other.description
		this.category = other.category
		this.genre = other.genre
		this.formatInstance = other.formatInstance
		
		return this
	}
	
	String toString() {
		"${typeid} ${name} ${description} ${category} ${genre} ${formatInstance}"
	}		
}
