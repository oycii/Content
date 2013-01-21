package com.oycii.content

import groovy.util.XmlSlurper
import javax.xml.parsers.DocumentBuilderFactory

/** Разобрать по полочкам игры playfon */
class TestUpdateGames {
	
	/** Разобрать по полочкам */
	def static parse(String path, String pathDTD) {
		def entityResolver = { a, b ->
			new org.xml.sax.InputSource(new File(pathDTD).newInputStream())
		} as org.xml.sax.EntityResolver

		def catalog = 
			new XmlParser(entityResolver: entityResolver).parse(new File(path))
	
		def count_groups = catalog.service_groups.service_group.size()
		println count_groups
		for (i in 1..count_groups) {
			println i
		}
			
		println catalog.@update_id + ' ' + catalog.@update_to + ' ' + catalog.@media_type + ' ' + catalog.@region		
		println catalog.service_groups.service_group.size()
		println catalog.@media_type
		println catalog.media_formats.media_format[0].@id + ' ' + catalog.media_formats.media_format[0].@name 
		
	}
	
	static void main (args) {
		println "Hello!"
		TestUpdateGames.parse("/opt/oycii/Doc/oycii/games.xml", "/opt/oycii/Doc/oycii/exportXML.dtd")
	}
}
