package com.oycii.content

import java.io.Serializable;
import java.util.Date;
import com.oycii.content.song.*;

class FormatInstance implements Serializable {
	int typeid
	Date dateCreated
	Date lastUpdated
	MediaFormat mediaFormat
	ServiceGroup serviceGroup
	
	static hasMany = [games: Game, songs: Song]
    
	static constraints = {
		typeid(blank:false, unique: true, index: 'fi_typeid_idx')
		mediaFormat(nullable: true)
	}
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof FormatInstance)) return false
		if (typeid != other.typeid) return false
		if (mediaFormat != other.mediaFormat) return false
		if (serviceGroup != other.serviceGroup) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof FormatInstance)) 
			throw new IllegalArgumentException('Put object is not FormatInstance')
			
		this.typeid = other.typeid
		this.mediaFormat = other.mediaFormat
		this.serviceGroup = other.serviceGroup
		
		return this
	}
	
	String toString() {
		"${typeid} ${mediaFormat} ${serviceGroup}"
	}	
}
