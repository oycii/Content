package com.oycii.content.data

import com.oycii.content.load.ContentUrl;

class ContentData {
	int updateId
	int updateTo
	String dir
	String path
	String url
	ContentUrl contentUrl

	@Override
	public String toString() {
		return "ContentData [updateId=" + updateId + ", updateTo=" + updateTo
				+ ", path=" + path + ", dir=" + dir + ", url=" + url
				+ ", contentUrl=" + contentUrl + "]";
	}
}
