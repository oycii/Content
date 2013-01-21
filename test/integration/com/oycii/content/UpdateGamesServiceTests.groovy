package com.oycii.content

import grails.test.*

class UpdateGamesServiceTests extends GrailsUnitTestCase {
	boolean transactional = false
	def updateGamesService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testParse() {
		updateGamesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/games.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd"
		/**
		updateGamesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/video.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd",
			1
		
		updateGamesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/themes.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd"

		updateGamesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/saver.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd"
		*/
    }
}
