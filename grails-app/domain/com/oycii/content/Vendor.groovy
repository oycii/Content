package com.oycii.content

import java.io.Serializable;

class Vendor implements Serializable {
	String name
	Date dateCreated
	Date lastUpdated	
	
	static hasMany = [phones: Phone]
    
	static constraints = {
		name(blank: false, index: 'vendor_name_idx')
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Vendor)) return false
		if (name != other.name) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Vendor)) 
			throw new IllegalArgumentException('Put object is not Vendor')
	
		this.name = other.name
		
		return this
	}
	
	String toString() {
		"${name}"
	}	
}
