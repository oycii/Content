package com.oycii.content.load

import java.io.Serializable;

/** Части загрузки контента */
class ContentUrlUpdatePart implements Serializable {

	int partId
	String url
	String path
	ContentUrlUpdate contentUrlUpdate
	int status
	
    static constraints = {
		partId(nullable: false)
		url(nullable: false)
		path(nullable: false)
		contentUrlUpdate(nullable: false)
		status(nullable: false)
    }
}
