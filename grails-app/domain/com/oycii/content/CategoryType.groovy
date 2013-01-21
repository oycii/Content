package com.oycii.content

import java.io.Serializable;

class CategoryType implements Serializable {
	
	public static final enum CategoryTypeItem {
		GAMES(1, "Java игры"),
		MELODY(2, "Мелодии"),
		IMG(3, 	"Изображения")
		
		private final int id
		private final String name
		
		CategoryTypeItem(int id, String name) {
			this.id = id
			this.name = name
		}
	}

	int categoryTypeId
	String name
	
    static constraints = {
    }
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof CategoryType)) return false
		if (categoryTypeId != other.categoryTypeId) return false
		if (name != other.name) return false
		
		return true
	}
	
	def leftShift(CategoryType other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof CategoryType))
			throw new IllegalArgumentException('Put object is not CategoryType')

		this.name = other.name
		this.categoryTypeId = other.categoryTypeId

		return this
	}
	
}
