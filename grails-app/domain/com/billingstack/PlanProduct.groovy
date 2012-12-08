package com.billingstack

import org.apache.commons.lang.builder.HashCodeBuilder

class PlanProduct implements Serializable {

	Plan plan

	Product product

	List rules = []
	
	static belongsTo = [
		plan : Plan,
		product : Product
	]

	static hasMany = [
		rules : PlanProductRule
	]

    static constraints = {
      plan()
      product()
      rules()
    }

	static mapping = {
		id composite: ['plan', 'product']
		rules cascade: 'all-delete-orphan'
		version false
	}

    def serialize() {
		def json = product.serialize()
		json.rules = rules.collect { rule ->
			rule.serialize()
		}
		json
    }

}