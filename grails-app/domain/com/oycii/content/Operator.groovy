package com.oycii.content


import java.io.Serializable;

class Operator implements Serializable {
	String name
	String logoLink
	Date dateCreated
	Date lastUpdated

	static hasMany = [services: Service]
	
    static constraints = {
		name(blank:false, unique: true, index: 'operator_name_idx')
		logoLink(url: true)
    }
		
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Operator)) return false
		if (name != other.name) return false
		if (logoLink != other.logoLink) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Operator)) 
			throw new IllegalArgumentException('Put object is not Operator')
			
		this.name = other.name
		this.logoLink = other.logoLink
		
		return this
	}
	
	String toString() {
		"${name} ${logoLink}"
	}	
}
