package com.billingstack

import org.apache.commons.lang.builder.HashCodeBuilder

class PlanProduct implements Serializable {

	Merchant merchant

	Plan plan

	Product product

    static constraints = {
      plan()
      product()
    }

	static mapping = {
		id composite: ['plan', 'product']
		version false
	}

    def serialize() {
		def json = product.serialize()
		json.rules = PlanProductRule.findAllByProduct(this).collect { rule ->
			rule.serialize()
		}
		json
    }

}