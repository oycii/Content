package com.oycii.content

import java.io.Serializable;

class Service implements Serializable {
	int typeid
	String phone
	String realPrice
	Date dateCreated
	Date lastUpdated
	
	Operator operator
	ServiceGroup serviceGroup
	
    static constraints = {
		typeid(blank:false, unique: true, index: 'service_typeid_idx')
		serviceGroup(nullable: true)
		operator(nullable: true)
		serviceGroup(nullable: true)
    }
		
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Service)) return false
		if (typeid != other.typeid) return false
		if (phone != other.phone) return false
		if (operator != other.operator) return false
		if (serviceGroup != other.serviceGroup) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Service)) 
			throw new IllegalArgumentException('Put object is not Service')
			
		this.typeid = other.typeid
		this.phone = other.phone
		this.realPrice = other.realPrice
		this.operator = other.operator
		this.serviceGroup = other.serviceGroup
		
		return this
	}
	
	String toString() {
		"${typeid} ${phone} ${realPrice} ${operator} ${serviceGroup}"
	}
	
}
