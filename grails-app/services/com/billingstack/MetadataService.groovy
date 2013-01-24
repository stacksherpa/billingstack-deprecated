package com.billingstack

class MetadataService {
	
		def list(BillingEntity entity) {
			Metadata.findAllByEntity(entity)
		}
	
		def show(BillingEntity entity, key) {
			Metadata.findByEntityAndKey(entity, key)
		}

    def update(BillingEntity entity, String key, String value) {
			def metadata = Metadata.findOrCreateByEntityAndKey(entity, key)
			metadata.value = value
			metadata.save(failOnError : true)
    }

		def update(BillingEntity entity, metadata) {
			metadata.each { k, v ->
				update(entity, k, v)
			}
			metadata
    }

		def delete(String id) {
			Metadata.load(id).delete(flush : true)
    }
}
