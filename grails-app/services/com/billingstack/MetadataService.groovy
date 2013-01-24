package com.billingstack

import grails.converters.JSON

class MetadataService {
	
		def list(BillingEntity entity) {
			entity.metadata ? JSON.parse(entity.metadata) : [:]
		}
	
		def show(BillingEntity entity, String key) {
			def metadata = list(entity)
			entity[key]
		}

    def update(BillingEntity entity, String key, value) {
			def metadata = list(entity)
			entity[key] = value
			entity.metadata = metadata.toString()
			entity.save(failOnError : true)
    }

		def update(BillingEntity entity, metadata) {
			entity.metadata = metadata.toString()
    }

		def delete(BillingEntity entity, String key) {
			def metadata = list(entity)
			entity.remove(key)
			entity.save(failOnError : true)
    }
}
