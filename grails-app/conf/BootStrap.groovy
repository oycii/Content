import com.oycii.content.security.*;

class BootStrap {
	com.oycii.content.UpdateGamesService updateGamesService
	com.oycii.content.UpdateSongsService updateSongsService
	com.oycii.content.UpdateImagesService updateImagesService
	
	def addAdminUser() {
		def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true) 
		def moderatorRole = new Role(authority: 'ROLE_MODERATOR').save(flush: true)
		
		def sanyaUser = new User(username: 'sanya', enabled: true, password: 'javaobject') 
		def yuliyaUser = new User(username: 'yuliya', enabled: true, password: 'javaobject')
		
		sanyaUser.save(flush: true)
		yuliyaUser.save(flush: true)
		
		UserRole.create sanyaUser, adminRole, true
		UserRole.create sanyaUser, moderatorRole, true

		UserRole.create yuliyaUser, moderatorRole, true
				
		assert User.count() == 2 
		assert Role.count() == 2 
		assert UserRole.count() == 3
	}
	
     def init = { servletContext ->
     	/**
 		updateGamesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/games.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd"
		updateSongsService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/melody.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd"
		updateImagesService.parse "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/images.xml", 
			"/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd", 0
		*/
			
        //create admin user
		addAdminUser()
     }
	 
     def destroy = {
     }
} 