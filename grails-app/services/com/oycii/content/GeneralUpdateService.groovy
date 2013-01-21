package com.oycii.content

import org.springframework.transaction.annotation.Transactional;

import com.oycii.content.CategoryType.CategoryTypeItem
import com.oycii.content.State.StateItem
import groovy.util.Node;

@Transactional
class GeneralUpdateService {
    // boolean transactional = false
	
	GeneralDaoService generalDaoService

	/** Сохранить ценовые форматы */
	void saveFormatInstances(Node catalog) {
		for (fi in 0..(catalog.format_instances.format_instance.size() - 1)) {
			log.info 'service group of format instance: ' + 
				catalog.format_instances.format_instance[fi].@sg_id
				
			MediaFormat mediaFormat = MediaFormat.findByTypeid(
				catalog.format_instances.format_instance[fi].@mf_id)
			log.info "mediaFormat: ${mediaFormat}"
			ServiceGroup serviceGroup = ServiceGroup.findByTypeid (
				catalog.format_instances.format_instance[fi].@sg_id)
			log.info "serviceGroup: ${serviceGroup}"
			
			FormatInstance formatInstance = generalDaoService.addObject(
				new FormatInstance(
					typeid: catalog.format_instances.format_instance[fi].@id,
					mediaFormat: mediaFormat,
					serviceGroup: serviceGroup
				),
				FormatInstance.findByTypeid(catalog.format_instances.format_instance[fi].@id),
				'Invalid or empty FormatInstance'
			)
			
			log.info "Added formatInstance: ${formatInstance}"
		}		
	}

	/** Сохранить медиа форматы */
	void saveMediaFormats(Node catalog, Category category) {
		log.info "Start saveMediaFormats size: " + catalog.media_formats.media_format.size()
		for (i in 0..catalog.media_formats.media_format.size() - 1) {			
			MediaFormat mediaFormat = generalDaoService.addObject(
				new MediaFormat(
					typeid: catalog.media_formats.media_format[i].@id,
					name: catalog.media_formats.media_format[i].@name,
					codeMask: catalog.media_formats.media_format[i].@code_mask,
					codeMaskOld: catalog.media_formats.media_format[i].@code_mask_old,
					category: category),
				MediaFormat.findByTypeid(catalog.media_formats.media_format[i].@id),
				'Invalid or empty mediaFormat'
			)
			
			log.info "save saveMediaFormats: ${mediaFormat}"
		}		
	}
	
	/** Загрузить сервисные группы */
	void saveServiceGroups(Node catalog) {
		log.info "Start saveServiceGroups"
		for (i in 0..(catalog.service_groups.service_group.size() - 1)) {
			ServiceGroup serviceGroup = generalDaoService.addObject(
				new ServiceGroup(
					typeid: catalog.service_groups.service_group[i].@id,
					price: catalog.service_groups.service_group[i].@price
				),
				ServiceGroup.findByTypeid(catalog.service_groups.service_group[i].@id),
				'Invalid or empty serviceGroup'
			)
			
			log.info "service group ${i}: ${serviceGroup}"
			
			for (q in 0..(catalog.service_groups.service_group[i].service.size() - 1)) {
				Operator operator = generalDaoService.addObject(
					new Operator( 
						name: catalog.service_groups.service_group[i].service[q].@operator_name,
						logoLink: catalog.service_groups.service_group[i].service[q].@operator_logo
					),
					Operator.findByName(catalog.service_groups.service_group[i].service[q].@operator_name),
					'Invalid or empty operator'
				)
				
				Service service = generalDaoService.addObject(
					new Service(
						typeid: catalog.service_groups.service_group[i].service[q].@id,
						phone: catalog.service_groups.service_group[i].service[q].@phone,
						realPrice: catalog.service_groups.service_group[i].service[q].@real_price,
						operator: operator,
						serviceGroup: serviceGroup
					),
					Service.findByTypeidAndServiceGroup(
							catalog.service_groups.service_group[i].service[q].@id, serviceGroup),
					'Invalid or empty service'
				)				
			}
		}		
	}
	
	/** Загрузить жанры */
	void saveGenres(Node catalog, Category category, CategoryType categoryType) {
		log.info "Start saveGenres with category ${category}"
		// Загрузим жанры
		for (g in 0..(catalog.genres.genre.size() - 1)) {
			def oldGenre = Genre.findByNameAndCategory(
					catalog.genres.genre[g].@name, 
					category, 
					[cache: true])
			
			log.info "oldGenre: ${oldGenre}"
			
			Genre genre = generalDaoService.addObject(
				new Genre(
					typeid: catalog.genres.genre[g].@id,
					name: catalog.genres.genre[g].@name,
					category: category,
					categoryType: categoryType
				),
				oldGenre,
				'Invalid or empty genre'
			)
		}
	}
	
	/** Загрузить телефоны */
	void savePhones(Node catalog) {
		log.info 'phones size: ' + catalog.phones.phone.size()
		
		if (catalog.phones.phone.size() == 0)
			return
			
		// Получим все телефоны
		List phoneList = Phone.list()
		HashMap mapPhones = [:]
		phoneList.each{ it -> mapPhones[it.typeid] = it}
		
		List vendorList = Vendor.list()
		HashMap mapVendors = [:]
		vendorList.each{ it -> mapVendors[it.name] = it}
		
		for (m in 0..(catalog.phones.phone.size() - 1)) {
			Vendor vendor = generalDaoService.addObject(
				new Vendor(name: catalog.phones.phone[m].@vendor),
				mapVendors[catalog.phones.phone[m].@vendor],
				// Vendor.findByName(catalog.phones.phone[m].@vendor, [cache: true]),
				'Invalid or empty Vendor'
			)
				
			int phoneTypeId = catalog.phones.phone[m].@id.toInteger()
			Phone phone = generalDaoService.addObject(
				new Phone(typeid: phoneTypeId,
					model: catalog.phones.phone[m].@model,
					vendor: vendor
				),
				mapPhones[phoneTypeId],
				//Phone.findByTypeid(catalog.phones.phone[m].@id, [cache: true]),
				'Invalid or empty Phone'
			)			
		}		
	}
	
	/** Загрузить принадлежность Медиа форматов моделям телефонов */
	void savePhonesOfMediaFormats(Node catalog) {
    	log.info "Start saveMediaFormats"
		for (i in 0..catalog.media_formats.media_format.size() - 1) {			
			MediaFormat mediaFormat = MediaFormat.findByTypeid(catalog.media_formats.media_format[i].@id)
			mediaFormat.previewName = catalog.media_formats.media_format[i].@preview_field
			mediaFormat.save()
			
			log.info "saveMediaFormat: ${mediaFormat}"
			
			// Сохранить пренадлежность телефонам песен
			def phones = catalog.media_formats.media_format[i].@supported_phones.split(',')
			for (p in 0..(phones.size() - 1)) {
				Phone phone = Phone.findByTypeid(phones[p], [cache: true])
				mediaFormat.addToPhones(phone)
				log.info "Add phone ${phone} to ${mediaFormat}"
			}		

			log.info "save phones of MediaFormat ${mediaFormat}"
		}
	}
	
	
	/** Загрузить  */
	
	def saveGeneralData(Node catalog) {
		// Добавить страну
		StateItem stateItem = State.getByCode(catalog.@region.toString())		
		State state = generalDaoService.addObject(
			new State(name: stateItem.name, region: catalog.@region, status: 0), 
			State.findByRegion(catalog.@region), 
			'Invalid or empty state')

		log.info "Added state: ${state}"
		
		// Сохранить версию
		UpdateVersion updateVersion = generalDaoService.addObject(
			new UpdateVersion(
				versionid: catalog.@update_id,
				versionto: catalog.@update_to
			),
			UpdateVersion.findByVersionto(
				catalog.@update_to
			),
			'Invalid or empty update'
		)
		
		log.info "Added updateVersion: ${updateVersion}"
		
		// Добавим тип категории
		CategoryTypeItem categoryTypeItem = Category.getCategoryTypeItem(catalog.@media_type)
		log.info "categoryTypeItem: ${categoryTypeItem}, id: ${categoryTypeItem.id}, name: ${categoryTypeItem.name}"
		
		CategoryType oldCategoryType = CategoryType.findByCategoryTypeId(categoryTypeItem.id)
		log.info "oldCategoryType: ${oldCategoryType}"

		CategoryType newCategoryType = new CategoryType(categoryTypeId: categoryTypeItem.id, name: categoryTypeItem.name)
		log.info "newCategoryType: ${newCategoryType}"
				
		CategoryType categoryType = generalDaoService.addObject(
			newCategoryType, 
			oldCategoryType, 
			"Error add categoryType")
				
		Category category = generalDaoService.addCategory(
			new Category(
				name: catalog.@media_type, 
				status: 0,
				updateVersion: updateVersion,
				state: state,
				categoryType: categoryType)
		)
		
		log.info "Added category: ${category}"
		
		// Сохранить медиа форматы
		saveMediaFormats(catalog, category)
		MediaFormat mediaFormat = MediaFormat.findByName(catalog.@media_type)
		
		log.info "Added mediaFormat: ${mediaFormat}"
						
		// Сохранить сервис
		saveServiceGroups(catalog)
		log.info "Added ServiceGroups"
		
		// Сохранить ценовые форматы
		saveFormatInstances(catalog)
		log.info "Added FormatInstances" 
											
		// Загрузить жанры
		saveGenres(catalog, category, categoryType)
		log.info "Added Genres" 
	
		// Загрузим модели телефонов
		savePhones(catalog)
		log.info "Added Phones"	
		
		return category	
	}
}
