package com.oycii.content

import groovy.util.XmlSlurper
import javax.xml.parsers.DocumentBuilderFactory

/** Разобрать по полочкам игры playfon */
class PhoneListOfMediaFormat {
    
    /** Разобрать по полочкам */
    def static parse(String path, String pathDTD) {
        def entityResolver = { a, b ->
            new org.xml.sax.InputSource(new File(pathDTD).newInputStream())
        } as org.xml.sax.EntityResolver

        def catalog = new XmlParser(entityResolver: entityResolver).parse(new File(path))
        /**
        println catalog.service_groups.service_group.size()
        println catalog.@media_type
        println 'typeid: ' + catalog.items.item[0].@id + 
                ', description: ' + catalog.items.item[0].description.text()
        println 'screenshort size: ' + catalog.items.item[0].screenshots.screenshot.size()
        println 'x: ' + 144 + ', typeid: ' + catalog.items.item[144].@id + 
                ', description: ' + catalog.items.item[144].description[0].text()
        println 'games size: ' + catalog.items.item.size() */
        
        // Соберём все телефоны
		/**
        ArrayList phones = []
        for (x in 0..(catalog.items.item.size() - 1)) {
            if (catalog.items.item[x].@supported_phones != null) {
                def game_phones = catalog.items.item[x].@supported_phones.split(',')
                phones.addAll(game_phones)
            }
        } */
        
        for (m in 0..(catalog.phones.phone.size() - 1)) {
            // if (phones.contains(catalog.phones.phone[m].@id)) {
                print "${catalog.phones.phone[m].@id},"
            // } 
        }

    }
    
    static void main (args) {
        println "Hello!"
        PhoneListOfMediaFormat.parse("/opt/oycii/projects/Partner/Content/web-app/data/RU/Melody/0/0.xml", 
            "/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd")
    }
}