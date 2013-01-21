package com.oycii.content.load

class TestLoadController {
	// http://localhost:8080/Content/data/RU/Games/0/0.xml

	
    def index() {
		
		String state = params.region
		String contentType = params.contenttype
		String dir = params.update_id
		String part = params.part
	
		if (part == null) {
			redirect(url: "http://localhost:8080/Content/data/${state}/${contentType}/${dir}/0.xml")
		} else {
			redirect(url: "http://localhost:8080/Content/data/${state}/${contentType}/${dir}/${part}.xml")
		}
	}
}
