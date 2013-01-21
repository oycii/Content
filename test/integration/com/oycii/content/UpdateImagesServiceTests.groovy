package com.oycii.content

import grails.test.*

class UpdateImagesServiceTests extends GrailsUnitTestCase {
	boolean transactional = true
	def updateImagesService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testParse() {
		/** 
		updateImagesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/images.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd", 
			0
		
		updateImagesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/logo.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd", 
			1
		*/	
    }
}
