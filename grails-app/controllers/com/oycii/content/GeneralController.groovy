package com.oycii.content

class GeneralController {
	def generalService
	
    def index = { redirect(action: vendors) }
	
	def vendors = {
		log.info "Start vendors, category: $params.category"
		def vendors = Vendor.list()
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)
		
		return [vendors: vendors, genres: genres, category: params.category]
	}
	
	def phones = {
		log.info "Start vendors"
		def currentCategory = generalService.getCurrentCategory(params)
		def genres = generalService.genres(currentCategory)
		def phones
		if (params.vendorid != null) {
			def vendor = Vendor.get(params.vendorid)
			phones = Phone.findAllByVendor(vendor)
		} else {
			phones = Phone.list()
		}
		
		return [phones: phones, genres: genres, category: params.category]
	}	
}
