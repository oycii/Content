package com.oycii.content

import org.springframework.transaction.annotation.Transactional;

import groovy.util.Node;
import com.oycii.content.song.*;

@Transactional
class UpdateSongsService {
	// boolean transactional = false
	
	def generalDaoService
	def generalUpdateService	

	
	/** Сохранить примеры песен */
	void saveSongPreviews(Node catalog, Song song, int x) {
		def size = catalog.items.item[x].previews.preview.size()
		if (size == 0)
			return
		log.info 'song previews size: ' + catalog.items.item[x].previews.preview.size()
		
		for (p in 0..(catalog.items.item[x].previews.preview.size() - 1)) {
			SongPreviewFormat songPreviewFormat = generalDaoService.addObject(
				new SongPreviewFormat(
					name: catalog.items.item[x].previews.preview[p].@name, 
					status: 0), 
				SongPreviewFormat.findByName(catalog.items.item[x].previews.preview[p].@name, [cache: true]), 
				'Invalid or empty SongPreviewFormat'
			)
			
			log.info "songPreviewFormat: ${songPreviewFormat}"

			SongPreview songPreview = generalDaoService.addObject(
				new SongPreview(
					url: catalog.items.item[x].previews.preview[p].@url, 
					status: 0,
					song: song,
					songPreviewFormat: songPreviewFormat), 
				SongPreview.findByUrlAndSongPreviewFormat(
						catalog.items.item[x].previews.preview[p].@url,
						songPreviewFormat, [cache: true]
				), 
				'Invalid or empty songPreview'
			)
			
			log.info "SongPreview: ${songPreview}"
		}
	}
	
	/** Добавить форматы песне */
	void addSongFormatInstances(Node catalog, Song song, int x) {
		def formatInstances = catalog.items.item[x].@fi_ids.split(',')
		log.info "Add formats to song : ${formatInstances.size()}"
		
		for (f in 0..(formatInstances.size() - 1)) {
			FormatInstance formatInstance = FormatInstance.findByTypeid(
				formatInstances[f], [cache: true])
			
			if (formatInstance != null) {
				song.addToFormatInstances(formatInstance)
				log.info "Add formatInstance ${formatInstance} to ${song}"
			}
		}
	}
	
	/** Сохранить песни */
    void saveSongs(Node catalog, Category category) {
		log.info 'Start saveGames song size: ' + catalog.items.item.size()
		for (x in 0..(catalog.items.item.size() - 1)) {
			log.info 'x: ' + x + ', typeid: ' + catalog.items.item[x].@id + 
				', name: ' + catalog.items.item[x].@name

			Genre genre = Genre.findByTypeid(catalog.items.item[x].@genre_id, 
				[cache: true])
			log.info "genre ${catalog.items.item[x].@genre_id}: ${genre}"
			
			Singer singer = (catalog.items.item[x].@singer != null)? generalDaoService.addObject(
				new Singer(
					name: catalog.items.item[x].@singer.replaceAll('"', ' '),
					status: 0
				), 
				com.oycii.content.song.Singer.findByName(
					catalog.items.item[x].@singer.replaceAll('"', ' '), 
					[cache: true]), 
				'Invalid or empty singer'
			):null
			
			log.info "singer: ${singer}"
		
			// FormatInstance formatInstance = 
			// FormatInstance.findByTypeid(catalog.items.item[x].@fi_ids, [cache: true])
			// log.info "formatInstance: ${formatInstance}"
			
			Song newSong = new Song(
				typeid: catalog.items.item[x].@id,
				name: catalog.items.item[x].@name,
				status: 0,
				singer: singer,
				category: category,
				genre: genre,
				state: category.state
			) 

			log.info "newSong: ${newSong}"
			
			Song oldSong = Song.findByTypeid(catalog.items.item[x].@id, [cache: true])
			log.info "oldSong: ${oldSong}"
			
			Song song = generalDaoService.addObject(
				newSong,
				oldSong,
				'Invalid or empty song'
			)
						
			log.info "song: ${song}"
			
			if (song != null) {
				// Добавить форматы песне
				addSongFormatInstances(catalog, song, x)
									
				// Сохранить превью песни
				saveSongPreviews(catalog, song, x)
			}
		}		
	}

	def upload(Node catalog) {
		// Загрузим общие данные
		Category category = generalUpdateService.saveGeneralData(catalog)
		
		// Загрузить принадлежность Медиа форматов моделям телефонов
		generalUpdateService.savePhonesOfMediaFormats(catalog)
		
		// Загрузим игры
		saveSongs(catalog, category)
		log.info "Added Songs"
	}
	
    void parse(String path, String pathDTD) {
		log.info "Start parse"
		def entityResolver = { a, b ->
			new org.xml.sax.InputSource(new File(pathDTD).newInputStream())
		} as org.xml.sax.EntityResolver

		Node catalog = 
			new XmlParser(entityResolver: entityResolver).parse(new File(path))

		log.info "complite parse file: ${path}"
		
		upload(catalog)
    }
}
