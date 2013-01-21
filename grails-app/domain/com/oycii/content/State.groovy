package com.oycii.content

import java.io.Serializable;

class State implements Serializable {
	public static final int DEFAULT_STATE_ID = 1
	
	public static final enum StateItem {
		RU(1, "RU", "Россия"),
		UA(2, "UA", "Украина"),
		KZ(3, "KZ", "Казахстан"),
		AZ(4, "AZ", "Азербайджан"),
		AM(5, "AM", "Армения"),
		BY(6, "BY", "Беларусь")
		
		private final int id
		private final String code
		private final String name
		StateItem(int id, String code, String name) {
			this.id = id
			this.code = code
			this.name = name
		}
	}
	
	int stateId
	String name
	String region
	int status
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [categories: Category]
	
    static constraints = {
		stateId(blank:false, unique: true, index: 'state_id_idx')
		name(blank:false, index: 'state_name_idx')
		region(blank:false, unique: true, index: 'state_region_idx')
		status(blank:false)
    }
	
	static StateItem getByCode(String code) {
		for (stateItem in State.StateItem.values()) {
			if (code == stateItem.code)
				return stateItem
		}
	}
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof State)) return false
		if (name != other.name) return false
		if (region != other.region) return false
		if (status != other.status) return false
		
		return true
	}
	
	def leftShift(Object other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof State)) 
			throw new IllegalArgumentException('Put object is not State')
			
		this.name = other.name
		this.region = other.region
		this.status = other.status
		
		return this
	}
	
	String toString() {
		"${name} ${region} ${status}"
	}
}
