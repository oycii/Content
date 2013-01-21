package com.oycii.content

import groovy.util.XmlSlurper
import javax.xml.parsers.DocumentBuilderFactory

import com.oycii.content.Category;
import com.oycii.content.FormatInstance;
import com.oycii.content.Game;
import com.oycii.content.Genre;
import com.oycii.content.MediaFormat;
import com.oycii.content.Operator;
import com.oycii.content.Phone;
import com.oycii.content.Screenshot;
import com.oycii.content.Service;
import com.oycii.content.ServiceGroup;
import com.oycii.content.State;
import com.oycii.content.UpdateVersion;
import com.oycii.content.Vendor;

/** Разобрать по полочкам игры playfon */
class UpdateGames {
    
	/** Добавить оператора */
	def addOperator(String name, String logoLink) {
		def operator = Operator.findByName(name)
		if (operator == null)
			operator = new Operator(name: name, logoLink: logoLink).save()
			
		return operator
	}
	
	/** Добавить вендора */
	def addVendor(String name) {
		def vendor = Vendor.findByName(name)
		if (vendor == null)
			vendor = new Vendor(name: name).save()
			
		return vendor
	}
	
	
	/** Разобрать по полочкам */
	def parse(String path, String pathDTD) {
		def entityResolver = { a, b ->
			new org.xml.sax.InputSource(new File(pathDTD).newInputStream())
		} as org.xml.sax.EntityResolver

		def catalog = 
			new XmlParser(entityResolver: entityResolver).parse(new File(path))
			
		def state = new State(name: catalog.@region, 
			region: catalog.@region, status: 0)
		state.save()
		def updateVersion = new UpdateVersion(versionid: catalog.@update_id, 
			versionto: catalog.@update_to).save()

		def category = new Category(name: catalog.@media_type , status: 0)
		
		for (i in 0..catalog.media_formats.media_format.size() - 1) {
			def mediaFormat = new MediaFormat(
				typeid: catalog.media_formats.media_format[i].@id,
				name: catalog.media_formats.media_format[i].@name,
				codeMask: catalog.media_formats.media_format[i].@code_mask,
				codeMaskOld: catalog.media_formats.media_format[i].@code_mask_old
			).save()
			
			mediaFormat.addToCategories(category)
		}
			
		for (i in 0..(catalog.service_groups.service_group.size() - 1)) {
			def serviceGroup = new ServiceGroup(
				typeid: catalog.service_groups.service_group[i].@id,
				price: catalog.service_groups.service_group[i].@price
			).save() 
			
			log.info 'service group ' + i + ': ' + serviceGroup
			
			for (q in 0..(catalog.service_groups.service_group[i].service.size() - 1)) {
				def service = new Service(
					typeid: catalog.service_groups.service_group[i].service[q].@id,
					phone: catalog.service_groups.service_group[i].service[q].@phone,
					realPrice: catalog.service_groups.service_group[i].service[q].@real_price
				).save()
				
				def operator = addOperator(
					catalog.service_groups.service_group[i].service[q].@operator_name,
					catalog.service_groups.service_group[i].service[q].@operator_logo)
				
				operator.addToServices(service)
			}
		}
		
		// Загрузим экземляры форматов
		for (fi in 0..(catalog.format_instances.format_instance.size() - 1)) {
			log.info 'service group: ' + catalog.format_instances.format_instance[fi].@sg_id
			def mediaFormat = MediaFormat.findByTypeid(
				catalog.format_instances.format_instance[fi].@mf_id)
			def serviceGroup = ServiceGroup.findByTypeid (
				catalog.format_instances.format_instance[fi].@sg_id)
			
			def formatInstance = new FormatInstance(
				typeid: catalog.format_instances.format_instance[fi].@id).save()
			
			log.info 'service group: ' + serviceGroup
			formatInstance.addToServiceGroupes(serviceGroup)
			mediaFormat.addToFormatInstances(formatInstance)
		}
		
		// Загрузим жанры
		for (g in 0..(catalog.genres.genre.size() - 1)) {
			def genre = new Genre(typeid: catalog.genres.genre[g].@id,
				name: catalog.genres.genre[g].@name
			).save()
		}
		
		// Загрузим модели телефонов
		log.info 'phones size: ' + catalog.phones.phone.size()
		for (m in 0..(catalog.phones.phone.size() - 1)) {
			Vendor vendor = addVendor(catalog.phones.phone[m].@vendor)
				
			def phone = new Phone(typeid: catalog.phones.phone[m].@id,
				 model: catalog.phones.phone[m].@model
			).save()
				
			vendor.addToPhones(phone)		
		}
		
		// Загрузим игры
		log.info 'games size: ' + catalog.items.item.size()
		for (x in 0..(catalog.items.item.size() - 1)) {
			log.info 'x: ' + x + ', typeid: ' + catalog.items.item[x].@id + 
				', name: ' + catalog.items.item[x].@name +
				', description: ' + catalog.items.item[x].description[0].text()

			def game = new Game(typeid: catalog.items.item[x].@id,
				name: catalog.items.item[x].@name,
				description: catalog.items.item[x].description[0].text()
			).save()
						
			log.info 'game: ' + game.id + ', typeid: ' + catalog.items.item[x].@id + 
				', description: ' + catalog.items.item[x].description[0].text()
			
			def genre = Genre.findByTypeid(catalog.items.item[x].@genre_id)
			log.info 'genre: ' + genre.name
			genre.addToGames(game)
			
			def phones = catalog.items.item[x].@supported_phones.split(',')
			log.info 'phones: ' + phones.size()
			for (p in 0..(phones.size() - 1)) {
				def phone = Phone.findByTypeid(phones[p])
				game.addToPhones(phone)			
			}
			
			def formatInstance = FormatInstance.findByTypeid(catalog.items.item[x].@fi_ids)
			log.info 'formatInstance: ' + formatInstance.id
			formatInstance.addToGames(game)
			
			log.info 'screenshort size: ' + catalog.items.item[x].screenshorts.screenshot.size()
			
			for (s in 0..(catalog.items.item[x].screenshots.screenshot.size() - 1)) {
				def defaultid = (catalog.items.item.screenshots.screenshot[s].@'default')?
						catalog.items.item.screenshots.screenshot[s].@'default':0
				def small = (catalog.items.item.screenshots.screenshot[s].@small)?
						catalog.items.item.screenshots.screenshot[s].@small:0
						
						
				def screenshot = new Screenshot(defaultid: defaultid, 
					url: catalog.items.item.screenshots.screenshot[s].@url,
					small: small
				).save()
				
				log.info 'screenshot: ' + screenshot.id
				game.addToScreenshots(screenshot)
			}
			
			category.addToGames(game)
		}
					
		println catalog.@update_id + ' ' + catalog.@update_to + ' ' + catalog.@media_type + ' ' + catalog.@region		
		println catalog.service_groups.service_group.size()
		println catalog.@media_type
	}
   
    static void main (args) {
        println "Hello!"
        def updateGames = new UpdateGames()
        updateGames.parse("/opt/oycii/Doc/oycii/games.xml", "/opt/oycii/Doc/oycii/exportXML.dtd")
    }
}