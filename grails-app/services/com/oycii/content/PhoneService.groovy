package com.oycii.content

import com.oycii.content.GamePhones;

class PhoneService {
	boolean transactional = false

	/** Сохранить телефоны игры в отдельном потоке */
	void savePhonesOfGame(GamePhones gamePhones) {
		Game game = Game.get(gamePhones.getGameid())
		def phones = gamePhones.getPhones().split(',')
		log.info 'phones: ' + phones.size()
		for (p in 0..(phones.size() - 1)) {
			Phone phone = Phone.findByTypeid(phones[p], [cache: true])
			game.addToPhones(phone)
			log.info "Added game: ${game.id}, phone: ${phone.id}"
		}
		game.save(flush:true)
		log.info "Added game: ${game}, phones: ${phones.size()}"
	}
}
