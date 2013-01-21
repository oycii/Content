package com.oycii.content.taglib


class GeneralTagLib {
	static namespace = "oycii"
	
	def pageCount = {  attrs ->
		
		def count = attrs.count
		def itemcount = attrs.itemcount
		def max = attrs.max
		def offset = attrs.offset
		
		log.info "pageCount attrs: ${attrs} "

		if ((max - itemcount) == 1 || itemcount < 2)
			out << "Кол-во: $count"
		else
			out << "Кол-во: $count (${offset +1} - ${offset + itemcount})"
	}
}
