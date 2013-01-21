package com.oycii.content.taglib

import com.oycii.content.ImageService;

class ScreenshotTagLib {
	static namespace = "oycii"
	
	def imageService
	
	def imageScreenshot = { attrs ->
		def image = attrs.image
		
		def sreenshots = imageService.getScreenshots(image)
		// log.info "${image.id} sreenshots size: ${sreenshots.size()}"
		
		for (i in 0..(sreenshots.size() - 1)) {
			def screenshot = sreenshots[i]
			// log.info "screenshot: ${sreenshots.url}"
			if  (screenshot.small == 0) {
				out << "<img class='preview' src='"
				out << screenshot?.url?.encodeAsHTML()
				out << "' />"
			}
		}
	}
}
