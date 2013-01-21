package com.oycii.content

import java.io.Serializable;

class UpdateVersion implements Serializable {
	int versionid
	int versionto
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [categories: Category]
	
    static constraints = {
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof UpdateVersion)) return false
		if (versionto != other.versionto) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof UpdateVersion)) 
			throw new IllegalArgumentException('Put object is not UpdateVersion')
			
		this.versionid = other.versionid
		this.versionto = other.versionto
		
		return this
	}
	
	String toString() {
		"${versionid} ${versionto}"
	}	
}
