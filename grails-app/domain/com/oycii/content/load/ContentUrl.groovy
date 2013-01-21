package com.oycii.content.load

import java.io.Serializable;

import com.oycii.content.State
import com.oycii.content.State.StateItem
import com.oycii.content.load.ContentType.ContentTypeItem


/** Url на данные с контентом */
class ContentUrl implements Serializable {
	public static final String PATH_EXPORT_DTD = "/opt/oycii/data/content/playfon/info/exportXML.dtd";
	
	public static final enum ContentUrlItem {
		
		/**
		GAMES_RU(1, RegionItem.RU, ContentTypeItem.GAMES, "http://wwwg.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		MELODY_RU(2, RegionItem.RU, ContentTypeItem.MELODY, "http://wwwm.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		REALTONES_RU(3, RegionItem.RU, ContentTypeItem.REALTONES, "http://wwwt.playfon.ru/exportXML.php?resource_id=363727&update_id="),
		MP3_RU(4, RegionItem.RU, ContentTypeItem.MP3, "http://wwwft.playfon.ru/exportXML.php?resource_id=363727&update_id="),
		IMG_RU(5, RegionItem.RU, ContentTypeItem.IMG, "http://wwwi.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		
		GAMES_UA(6, RegionItem.UA, ContentTypeItem.GAMES, "http://wwwg.ua.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		MELODY_UA(7, RegionItem.UA, ContentTypeItem.MELODY, "http://wwwm.ua.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		IMG_UA(8, RegionItem.UA, ContentTypeItem.IMG, "http://wwwm.ua.playfon.ru/exportXML.php?resource_id=123456&update_id="),

		GAMES_KZ(9, RegionItem.KZ, ContentTypeItem.GAMES, "http://wwwg.kz.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		MELODY_KZ(10, RegionItem.KZ, ContentTypeItem.MELODY, "http://wwwm.kz.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		IMG_KZ(11, RegionItem.KZ, ContentTypeItem.IMG, "http://wwwm.kz.playfon.ru/exportXML.php?resource_id=123456&update_id="),

		GAMES_AZ(12, RegionItem.AZ, ContentTypeItem.GAMES, "http://wwwg.az.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		MELODY_AZ(13, RegionItem.AZ, ContentTypeItem.MELODY, "http://wwwm.az.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		IMG_AZ(14, RegionItem.AZ, ContentTypeItem.IMG, "http://wwwm.az.playfon.ru/exportXML.php?resource_id=123456&update_id="),

		GAMES_AM(15, RegionItem.AM, ContentTypeItem.GAMES, "http://wwwg.am.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		MELODY_AM(16, RegionItem.AM, ContentTypeItem.MELODY, "http://wwwm.am.playfon.ru/exportXML.php?resource_id=123456&update_id="),
		IMG_AM(17, RegionItem.AM, ContentTypeItem.IMG, "http://wwwm.am.playfon.ru/exportXML.php?resource_id=123456&update_id="),

		GAMES_BY(18, RegionItem.BY, ContentTypeItem.GAMES, "http://wwwg.playfon.by/exportXML.php?resource_id=123456&update_id="),
		MELODY_BY(19, RegionItem.BY, ContentTypeItem.MELODY, "http://wwwm.playfon.by/exportXML.php?resource_id=123456&update_id="),
		IMG_BY(20, RegionItem.BY, ContentTypeItem.IMG, "http://wwwi.playfon.by/exportXML.php?resource_id=123456&update_id=")
		*/
		
		GAMES_RU(1, StateItem.RU, ContentTypeItem.GAMES, "http://localhost:8080/Content/testLoad/index?region=RU&contenttype=Games&resource_id=123456&update_id="),
		MELODY_RU(2, StateItem.RU, ContentTypeItem.MELODY, "http://localhost:8080/Content/testLoad/index?region=RU&contenttype=Melody&resource_id=123456&update_id="),
		REALTONES_RU(3, StateItem.RU, ContentTypeItem.REALTONES, "http://localhost:8080/Content/testLoad/index?region=RU&contenttype=Realtones&resource_id=363727&update_id="),
		MP3_RU(4, StateItem.RU, ContentTypeItem.MP3, "http://localhost:8080/Content/testLoad/index?region=RU&contenttype=Mp3&resource_id=363727&update_id="),
		IMG_RU(5, StateItem.RU, ContentTypeItem.IMG, "http://localhost:8080/Content/testLoad/index?region=RU&contenttype=Img&resource_id=123456&update_id=")
							
		private final int id
		private final StateItem stateItem
		private final ContentTypeItem contentTypeItem
		private final String url
		ContentUrlItem(int id, StateItem stateItem, ContentTypeItem contentTypeItem, String url) {
			this.id = id
			this.stateItem = stateItem
			this.contentTypeItem = contentTypeItem
			this.url = url
		}
	}
	
	int contentUrlId
	State state
	ContentType contentType
	String url	

    static constraints = {
		contentUrlId(blank:false, unique: true, index: 'content_url_id_idx')
		url(blank:false)
    }
}
