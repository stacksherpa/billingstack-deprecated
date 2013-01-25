package com.billingstack

import grails.converters.JSON

class MetadataApiController {
	
		def metadataService
		
		def show(String id) {
			render contentType : "application/json", text : BillingEntity.get(id).metadata ?: "{}"
		}

		def update(String id) {
			def entity = BillingEntity.get(id)
			metadataService.update(entity, request.JSON)
			show(id)
		}
}
