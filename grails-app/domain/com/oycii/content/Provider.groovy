package com.oycii.content


import java.io.Serializable;

class Provider implements Serializable {
	String name
	int status
	Date dateCreated
	Date lastUpdated	
	
    static constraints = {
		name(blank:false)
		status(blank:false)
    }
}
