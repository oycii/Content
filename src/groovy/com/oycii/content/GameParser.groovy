package com.oycii.content

import groovy.util.XmlSlurper
import javax.xml.parsers.DocumentBuilderFactory

/** Разобрать по полочкам игры playfon */
class GameParser {
	
	/** Разобрать по полочкам */
	def static parse(String path, String pathDTD) {
		def entityResolver = { a, b ->
			new org.xml.sax.InputSource(new File(pathDTD).newInputStream())
		} as org.xml.sax.EntityResolver

		def catalog = new XmlParser(entityResolver: entityResolver).parse(new File(path))
		println catalog.service_groups.service_group.size()
		println catalog.@media_type
		println 'typeid: ' + catalog.items.item[0].@id + 
				', description: ' + catalog.items.item[0].description.text()
		println 'screenshort size: ' + catalog.items.item[0].screenshots.screenshot.size()
		println 'x: ' + 144 + ', typeid: ' + catalog.items.item[144].@id + 
				', description: ' + catalog.items.item[144].description[0].text()
		println 'games size: ' + catalog.items.item.size()
	}
	
	static void main (args) {
		println "Hello!"
		GameParser.parse("/opt/oycii/projects/Partner/Content/doc/data/playfon/15/games.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd")
	}
}
