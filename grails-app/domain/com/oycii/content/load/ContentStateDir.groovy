package com.oycii.content.load

import java.io.Serializable;

import com.oycii.content.State
import com.oycii.content.State.StateItem


/** Каталог для контента определённого региона */
class ContentStateDir implements Serializable {
	public static final String CONTENT_DIR = "/opt/oycii/data/content/playfon"; 
	
	public static final enum ContentStateDirItem {
		RU(1, State.StateItem.RU, CONTENT_DIR + "/" + State.StateItem.RU.code),
		UA(2, State.StateItem.UA, CONTENT_DIR + "/" + State.StateItem.UA.code),
		KZ(3, State.StateItem.KZ, CONTENT_DIR + "/" + State.StateItem.KZ.code),
		AZ(4, State.StateItem.AZ, CONTENT_DIR + "/" + State.StateItem.AZ.code),
		AM(5, State.StateItem.AM, CONTENT_DIR + "/" + State.StateItem.AM.code),
		BY(6, State.StateItem.BY, CONTENT_DIR + "/" + State.StateItem.BY.code)
		
		private final int id
		private final StateItem stateItem
		private final String dir
		ContentStateDirItem(int id, StateItem stateItem, String dir) {
			this.id = id
			this.stateItem = stateItem
			this.dir = dir
		}
	}
	
	int contentStateDirId
	String dir
	State state

    static constraints = {
		contentStateDirId(blank:false, unique: true, index: 'state_dir_id_idx')
		dir(blank:false)
		state(nullable: false)
    }
}
