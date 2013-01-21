import com.oycii.content.*;

/** Загрузить данные */
class UploadService implements Upload  {
	static expose=['xfire']
	boolean transactional = false
	UpdateGamesService updateGamesService

    int add(int x, int y) {
		return x + y
    }
	
	int uploadGames(String path, String pathDTD, int type) {
		log.info "Start uploadGames"
		updateGamesService.parse(path, pathDTD, type)
		log.info "Finished uploadGames"
		return 1
	}
}
