package com.oycii.content


import java.io.Serializable;
import com.oycii.content.CategoryType.CategoryTypeItem
import com.oycii.content.song.*;

class Category implements Serializable {
	String name
	int status
	Date dateCreated
	Date lastUpdated
	
	UpdateVersion updateVersion
	State state
	CategoryType categoryType
	
	static hasMany = [games: Game, genres: Genre, songs: Song, mediaFormats: MediaFormat]
	
    static constraints = {
		name(blank:false, unique: true, index: 'category_name_idx')
		status(blank:false)
		updateVersion(nullable: true)
		state(nullable: true)
    }
	
	static mapping = {
		// mediaFormat lazy: false // Загружается вместе с категориями
	}
	
	/** Получить тип категории */
	public static CategoryTypeItem getCategoryTypeItem(String media_type) {
		switch (media_type) {
			case "games": return CategoryTypeItem.GAMES
			case "images": return CategoryTypeItem.IMG
			case "Polyphonia": return CategoryTypeItem.MELODY
			case "Full mp3": return CategoryTypeItem.MELODY
			case "Realtone": return CategoryTypeItem.MELODY
		}
		
		return null
	}
	
	boolean equals (Object other) {
		if (null == other) return false
		if (! (other instanceof Category)) return false
		if (name != other.name) return false
		if (status != other.status) return false
		if (updateVersion != other.updateVersion) return false
		if (state != other.state) return false
		
		return true
	}
	
	def leftShift(Category other) {
		if (null == other) throw new NullPointerException('Put object is null')
		if (! (other instanceof Category)) 
			throw new IllegalArgumentException('Put object is not Category')

		this.name = other.name			
		this.status = other.status
		this.updateVersion = other.updateVersion
		this.state = other.state
		
		return this
	}
	
	String toString() {
		"${name} ${status} ${updateVersion} ${state}"
	}	
}
