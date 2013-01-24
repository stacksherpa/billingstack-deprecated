package com.billingstack

import grails.converters.JSON

class MetadataApiController {
	
		def metadataService
		
		def show(String id) {
			def entity = BillingEntity.get(id)
			def metadata = [:]
			metadataService.list(entity).each {
				metadata[it.key] = it.value
			}
			render metadata as JSON
		}

		def update(String id) {
			def entity = BillingEntity.get(id)
			render metadataService.update(entity, request.JSON) as JSON
		}
}
