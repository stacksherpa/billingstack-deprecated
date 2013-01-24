package com.billingstack

class PlanProduct implements Serializable {
	
	Merchant merchant
	Plan plan
	Product product

	static constraints = {
		plan()
		product()
	}

	def findAllRules() {
		PlanProductRule.findAllByPlanAndProduct(plan, product)
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