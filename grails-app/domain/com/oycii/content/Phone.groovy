package com.oycii.content


import java.io.Serializable;

class Phone implements Serializable {
	int typeid
	String model
	Date dateCreated
	Date lastUpdated	
	
	Vendor vendor
	
	static hasMany = [games: Game, mediaFormats: MediaFormat]
    
	static constraints = {
		typeid(blank:false, unique: true, index: 'phone_typeid_idx')
		model(blank:false, unique: true)
		vendor(nullable: true)
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Phone)) return false
		if (typeid != other.typeid) return false
		if (model != other.model) return false
		if (vendor != other.vendor) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Phone)) 
			throw new IllegalArgumentException('Put object is not Phone')

		this.typeid = other.typeid			
		this.model = other.model
		this.vendor = other.vendor
		
		return this
	}
	
	String toString() {
		"${typeid} ${model} ${vendor}"
	}	
}
