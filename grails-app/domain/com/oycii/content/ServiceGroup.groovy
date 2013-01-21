package com.oycii.content

import java.io.Serializable;

class ServiceGroup implements Serializable {
	int typeid
	Double price
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [services: Service, formatInstances: FormatInstance]
	
    static constraints = {
		typeid(blank:false, unique: true, index: 'sg_typeid_idx')
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof ServiceGroup)) return false
		if (typeid != other.typeid) return false
		if (price != other.price) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof ServiceGroup)) 
			throw new IllegalArgumentException('Put object is not State')
			
		this.typeid = other.typeid
		this.price = other.price
		
		return this
	}
	
	String toString() {
		"${typeid} ${price}"
	}	
}
 