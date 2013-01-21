package com.oycii.content.load

import com.oycii.content.State
import com.oycii.content.UpdateGamesService
import com.oycii.content.UpdateImagesService
import com.oycii.content.UpdateSongsService
import com.oycii.content.data.ContentData
import com.oycii.content.load.ContentStateDir
import com.oycii.content.load.ContentStateDir.ContentStateDirItem
import org.springframework.transaction.annotation.Transactional
import com.oycii.content.Phone


@Transactional
class LoadService {
	boolean transactional = false
	UpdateGamesService updateGamesService
	UpdateSongsService updateSongsService
	UpdateImagesService updateImagesService 
		
	/** Загрузить регионы */
	def getStateList() {
		println "states: " + State.StateItem.values()
		for (stateItem in State.StateItem.values()) {
			println "stateItem: " + stateItem
			
			State state = State.findByStateId(stateItem.id) 
			if (state == null) {
				state = new State(stateId: stateItem.id, region: stateItem.code, name: stateItem.name)
				state.save(flush: true)
			}
			
			println state
		}
		
		return State.list()
	}
	
	/** Получить список типов контента */
	def getContentTypeList() {
		for (contentTypeItem in ContentType.ContentTypeItem.values()) {
			println "contentTypeItem: " + contentTypeItem
			def contentType = ContentType.findByContentTypeId(contentTypeItem.id)
				
			if (contentType == null) {
				contentType = new ContentType(contentTypeId: contentTypeItem.id, 
					categoryTypeId: contentTypeItem.categoryTypeId, name: contentTypeItem.name, 
					name_en: contentTypeItem.name_en)
				contentType.save(flush: true)
				println "contentType: " + contentType
			}
		}
			
		def contentTypeList = ContentType.list()
		return contentTypeList;
	}
	
	/** Создать каталоги для контента регионов */
	def createContentStateDir() {
		for (contentStateDirItem in ContentStateDir.ContentStateDirItem.values()) {
			println "contentStateDirItem: " + contentStateDirItem
			// Создадим директорию
			File input = new File(contentStateDirItem.dir)
			if (!input.exists() && !input.isDirectory()) {
				input.mkdir();
			}

			// Создадим запись в базе данных
			def contentStateDir = ContentStateDir.findByContentStateDirId(contentStateDirItem.id)
			if (contentStateDir == null) {
				def state = State.findByStateId(contentStateDirItem.stateItem.id)
				println "state: " + state
				contentStateDir = new ContentStateDir(contentStateDirId: contentStateDirItem.id, 
						dir: contentStateDirItem.dir, state: state)
				contentStateDir.save(flush: true)
				println "contentStateDir: " + contentStateDir
			} 
		}
	}
	
	/** Загрузим url для загрузки контента */
	def loadUrlContentList() {
		for (contentUrlItem in ContentUrl.ContentUrlItem.values()) {
			print "contentUrlItem: " + contentUrlItem  
			
			def contentUrl = ContentUrl.findByContentUrlId(contentUrlItem.id)
			if (contentUrl == null) {
				def state = State.findByStateId(contentUrlItem.stateItem.id)
				def contentType = ContentType.findByContentTypeId(contentUrlItem.contentTypeItem.id)
				def contentStateDir = ContentStateDir.findByState(state) 
				
				// Создадим директорию
				File input = new File(contentStateDir.dir + "/" + contentType.name_en)
				if (!input.exists() && !input.isDirectory()) {
					input.mkdir();
				}
				
				contentUrl = new ContentUrl(contentUrlId: contentUrlItem.id, state: state, contentType: contentType,
					url: contentUrlItem.url)
				contentUrl.save(flush: true)
	
			} 
		}
	}
	
	/** Получить путь к новому файлу контента */
	ContentData getContentDataLoad(State state, ContentType contentType, ContentStateDir contentStateDir,
		ContentUrl contentUrl, int lastContentUrlUpdateId) 
	{
		ContentUrlUpdate conetntUrlUpdate = 
			ContentUrlUpdate.findByUpdateIdAndContentUrl(lastContentUrlUpdateId, contentUrl)
		
		String dir = contentStateDir.dir + "/" + contentType.name_en + "/"
		String path = dir
		String url = contentUrl.url
		int updateId = lastContentUrlUpdateId
		if (conetntUrlUpdate == null) {
			dir = path + lastContentUrlUpdateId
			path = dir + "/0.xml"
			url = contentUrl.url + lastContentUrlUpdateId
		} else {
			dir = path + conetntUrlUpdate.updateTo
			path = dir + "/0.xml"
			url = contentUrl.url + conetntUrlUpdate.updateTo
			updateId = conetntUrlUpdate.updateTo
		}		 
		
		return new ContentData(contentUrl: contentUrl, updateId: updateId, dir: dir, path: path, url: url)  
	}
	
	/** Загрузить файл */
	boolean loadFile(String url, String filePathOut) {
		print "Start loadFile url: ${url}, filePathOut: ${filePathOut}"
		FileOutputStream fileOutputStream
		BufferedOutputStream out
		try {
			fileOutputStream = new FileOutputStream(filePathOut)
			out = new BufferedOutputStream(fileOutputStream)
			out << new URL(url).openStream()
			out.close()
			return true
		} catch (e) {
			if (fileOutputStream != null) {
				fileOutputStream.close()
			}
			
			// Удалим файл в случае ошибки
			File file = new File(filePathOut)
			if (file.isFile()) {
				file.delete()
			}
		}
		
		return false
	}
	
	/** Сохранить информации о загрузки данных */
	ContentUrlUpdate saveUpdateContentData(ContentData contentData, int status) {
		print "Start saveUpdateContentData: ${contentData}, status: ${status}"
		ContentUrlUpdate contentUrlUpdate = new ContentUrlUpdate(updateId: contentData.updateId, 
			updateTo: contentData.updateTo, contentUrl: contentData.contentUrl, status: status)
		contentUrlUpdate.save(flush: true)
		
		return contentUrlUpdate
	}
	
	/** Загрузить все части контента */
	List loadContentDataParts(ContentData contentData, Node catalog, ContentUrlUpdate contentUrlUpdate) {
		List parts = new ArrayList(10)
		
		// Проверим разбенение данных на части
		if (catalog.parts.part.size() > 0) {
			
			// Загрузим все части
			for (x in 0..(catalog.parts.part.size() - 1)) {
				def part_id = catalog.parts.part[x].@id
				def part_url = catalog.parts.part[x].@url
				String path = contentData.dir + "/" + part_id + ".xml"
				if (loadFile(part_url, path)) {
					ContentUrlUpdatePart contentUrlUpdatePart = new ContentUrlUpdatePart(partId: part_id, url: part_url,
						path: path, contentUrlUpdate: contentUrlUpdate, 
						status: ContentUrlUpdate.ContentUrlUpdateStatus.LOAD.id)
					contentUrlUpdatePart.save(flush: true)
					parts.add(contentUrlUpdatePart)
				}
			}
		}
		
		return parts
	}
	
	/** Создать каталог для загрузки данных */
	void createDir(String dir) {
		File newDir = new File(dir)
		if (!newDir.isDirectory()) {
			newDir.mkdir()
		}	
	}
	
	/** Сохранить данные */
	boolean updateData(ContentData contentData, Node catalog) {
		print "xml items size ${catalog.items.item.size()}"
		if (catalog.items.item.size() > 0) {
			switch (contentData.contentUrl.contentType.id) {
				case ContentType.ContentTypeItem.GAMES.id:
					print "Games: ${contentData}"
					updateGamesService.upload(catalog)
					break;
					
				case ContentType.ContentTypeItem.MELODY.id:
					print "Melody: ${contentData}"
					updateSongsService.upload(catalog)
					break;
					
				case ContentType.ContentTypeItem.REALTONES.id:
					print "Realtones: ${contentData}"
					updateSongsService.upload(catalog)
					break;
					
				case ContentType.ContentTypeItem.MP3.id:
					print "Mp3: ${contentData}"
					updateSongsService.upload(catalog)
					break;

				case ContentType.ContentTypeItem.IMG.id:
					print "Img: ${contentData}"
					updateImagesService.upload(catalog)
					break;
			}
			
			return true
		} else {
			File file = new File(contentData.path)
			file.delete()
		}
		
		return false
	}
	
	/** Загрузить данные */
	void loadContentData(ContentData contentData) {
		print "Start loadContentData ContentData: ${contentData}"
		// Создадим каталог для загрузки данных
		createDir(contentData.dir)
		// Загрузим файл
		if (loadFile(contentData.url, contentData.path) == false) {
			print "Error load file ${contentData.url}"
			return
		}
		print "load ${contentData.path}"

		// Выполним парсинг xml
		def entityResolver = { a, b ->
			new org.xml.sax.InputSource(new File(ContentUrl.PATH_EXPORT_DTD).newInputStream())
		} as org.xml.sax.EntityResolver
	
		Node catalog = new XmlParser(entityResolver: entityResolver).parse(new File(contentData.path))
		
		contentData.updateId = catalog.@update_id.toInteger()
		contentData.updateTo = catalog.@update_to.toInteger()
		
		print "xml parsed ${contentData}"
		
		// Сохраним информацию о загрузке файла
		ContentUrlUpdate contentUrlUpdate = saveUpdateContentData(contentData, 
			ContentUrlUpdate.ContentUrlUpdateStatus.LOAD.id)
		contentUrlUpdate.save(flush: true)		
		
		// Загрузить все части контента */
		List contentParts = loadContentDataParts(contentData, catalog, contentUrlUpdate)

		// Сохраним данные
		if (updateData(contentData, catalog)) {
			contentUrlUpdate.setStatus(ContentUrlUpdate.ContentUrlUpdateStatus.SAVE.id)
			contentUrlUpdate.save(flush: true)
			
			// Сохраним данные частей
			for (ContentUrlUpdatePart contentUrlUpdatePart: contentParts) {
				// Выполним парсинг xml
				entityResolver = { a, b ->
					new org.xml.sax.InputSource(new File(ContentUrl.PATH_EXPORT_DTD).newInputStream())
				} as org.xml.sax.EntityResolver
			
				catalog = new XmlParser(entityResolver: entityResolver).parse(new File(contentUrlUpdatePart.path))
				// Сохраним данные
				if (updateData(contentData, catalog)) {
					contentUrlUpdatePart.setStatus(ContentUrlUpdate.ContentUrlUpdateStatus.SAVE.id)
					contentUrlUpdatePart.save(flush: true)
				}
			}
		}
	} 
	
	/** Загрузим файлы с контентом */
	def loadContentFiles(stateList) {
		for (state in stateList) {
			print "loadContentFiles state: ${state}"
			// Получим список url на контент по определённому региону
			def contentUrlList = ContentUrl.findAllByState(state)
			for (contentUrl in contentUrlList) {
				ContentStateDir contentStateDir = ContentStateDir.findByState(state)
				ContentType contentType = ContentType.findByContentTypeId(contentUrl.contentType.id) 
							
				def lastContentUrlUpdateId = ContentUrlUpdate.executeQuery(
					"select max(cuu.updateId) from ContentUrlUpdate cuu where cuu.contentUrl = :contentUrl",
					[contentUrl: contentUrl ])[0]
				
				lastContentUrlUpdateId = 
					(lastContentUrlUpdateId == null)?ContentUrlUpdate.DEFAULT_UPDATE_ID:lastContentUrlUpdateId
					
				print "lastContentUrlUpdateId: ${lastContentUrlUpdateId}"
	   
				// Получить данные для загрузки контента
				ContentData contentData = getContentDataLoad(state, contentType, contentStateDir, contentUrl, 
					lastContentUrlUpdateId)
				
				// Загрузить данные
				loadContentData(contentData)
				
			}
		}
	}
	
	/** Загрузить данные */
    def load() {
		/**
		// Получим все телефоны
		List phoneList = []
		phoneList.add(new ContentData(updateId: 1))
		phoneList.add(new ContentData(updateId: 2))
		phoneList.add(new ContentData(updateId: 3))
		
		print "phoneList ${phoneList.size()}"
		
		HashMap mapPhones = [:]
		phoneList.each{ it -> mapPhones[it.updateId] = it}
		
		print "mapPhones ${mapPhones.size()}"
		print "mapPhones ${mapPhones[2]}" */

		// Загрузить регионы
		def stateList = getStateList()
		// Получим список типов контента
		def contentTypeList = getContentTypeList()
		// Создадим каталоги для контента
		createContentStateDir()
		// Загрузим url для загрузки контента 
		loadUrlContentList()
		// Загрузим файлы с контентом
		loadContentFiles(stateList)
    }
	
}
