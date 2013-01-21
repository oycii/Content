package com.oycii.content.load

import java.io.Serializable;

import com.oycii.content.load.ContentType.ContentTypeItem;

class ContentUrlUpdate implements Serializable {
	public final static int DEFAULT_UPDATE_ID = 0
	
	public final static enum ContentUrlUpdateStatus {
		LOAD(1), 
		SAVE(2)
		
		private final int id
		ContentUrlUpdateStatus(int id) {
			this.id = id
		}
		
	}
	
	int updateId
	int updateTo
	ContentUrl contentUrl
	int status

    static constraints = {
		updateId(blank:false)
		contentUrl(nullable: false)
		status(blank:false)
    }
}
