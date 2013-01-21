// includeTargets << grailsScript("_GrailsInit")

// target(main: "The description of the script goes here!") {
    
// }

// setDefaultTarget(main)

def loadFile(String url, String filePathOut) {
	def file = new FileOutputStream(filePathOut)
	def out = new BufferedOutputStream(file)
	out << new URL(url).openStream()
	out.close()
}

println "Start download!"
loadFile("http://wwwm.playfon.ru/exportXML.php?resource_id=123456&update_id=0", "/home/sanya/tmp/melody.xml")
UpdateSongsService updateSongsService

def path = "/opt/oycii/projects/Partner/Content/doc/data/playfon/test/melody.xml"
def	pathDTD = "/opt/oycii/projects/Partner/Content/doc/data/playfon/exportXML.dtd"

updateSongsService.parse(path, pathDTD)

println "it worked!"
