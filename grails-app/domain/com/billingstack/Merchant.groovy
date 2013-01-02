package com.billingstack

class Merchant extends BillingEntity {

	String name

	static constraints = {

	}

	def serialize(detail) {
		def json = [
			'id' : id,
			'name' : name,
		]
		json
	}
}
