package com.oycii.content


import java.io.Serializable;

class MediaFormat implements Serializable {
	int typeid
	String name
	String codeMask
	String codeMaskOld
	
	String previewName // Для песенок
	
	Date dateCreated
	Date lastUpdated
	
	Category category
	
	static belongsTo = Phone
	static hasMany = [formatInstances: FormatInstance, updateVersion: UpdateVersion, phones: Phone]
	
    static constraints = {
		typeid(blank:false, unique: true, index: 'mf_typeid_idx')
		codeMask(blank:false, unique: true)
		previewName(nullable: true)
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof MediaFormat)) return false
		if (typeid != other.typeid) return false
		if (name != other.name) return false
		if (codeMask != other.codeMask) return false
		if (codeMaskOld != other.codeMaskOld) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof MediaFormat)) 
			throw new IllegalArgumentException('Put object is not MediaFormat')
			
		this.typeid = other.typeid
		this.name = other.name
		this.codeMask = other.codeMask
		this.codeMaskOld = other.codeMaskOld
		
		return this
	}
	
	String toString() {
		"${typeid} ${name} ${codeMask} ${codeMaskOld}"
	}	
}
