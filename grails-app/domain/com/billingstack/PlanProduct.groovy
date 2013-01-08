package com.billingstack

import org.apache.commons.lang.builder.HashCodeBuilder

class PlanProduct implements Serializable {
	Plan plan
	Product product

	static constraints = {
		plan()
		product()
	}

	def findAllRules() {
		PlanProductRule.findAllByProduct(this)
	}

	public Merchant getMerchant() { 
		plan.getMerchant()
	}
	
	static mapping = {
		id composite: ['plan', 'product']
		version false
	}

	def serialize() {
		def json = product.serialize()
		json.rules = findAllRules()?.collect { rule ->
			rule.serialize()
		}
		json
	}
}