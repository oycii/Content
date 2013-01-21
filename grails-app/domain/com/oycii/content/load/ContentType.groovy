package com.oycii.content.load

import java.io.Serializable;

import com.oycii.content.CategoryType

class ContentType implements Serializable {
	
	public static final enum ContentTypeItem {
		GAMES(1, CategoryType.CategoryTypeItem.GAMES.id, "Java игры", "Games"),
		MELODY(2, CategoryType.CategoryTypeItem.MELODY.id, "Мелодии", "Melody"),
		REALTONES(3, CategoryType.CategoryTypeItem.MELODY.id, "Реалтоны", "Realtones"),
		MP3(4, CategoryType.CategoryTypeItem.MELODY.id, "Mp3", "Mp3"),
		IMG(5, CategoryType.CategoryTypeItem.IMG.id, "Картинки", "Img")
		
		private final int id
		private final int categoryTypeId
		private final String name
		private final String name_en
		ContentTypeItem(int id, int categoryTypeId, String name, String name_en) {
			this.id = id
			this.categoryTypeId = categoryTypeId
			this.name = name
			this.name_en = name_en 
		}
	}
	
	int contentTypeId
	int categoryTypeId
	String name
	String name_en
	
    static constraints = {
		contentTypeId(blank:false, unique: true, index: 'content_type_id_idx')
		name(blank:false, unique: true)
    }
}
