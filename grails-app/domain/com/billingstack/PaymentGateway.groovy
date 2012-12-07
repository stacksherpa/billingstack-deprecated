package com.billingstack

import grails.converters.JSON

class PaymentGateway extends BillingEntity {

	Merchant merchant

	String name

	String title

	String description

	Boolean isDefault = Boolean.FALSE
	
	String metadata

	static belongsTo = [
		merchant : Merchant
	]

	static constraints = {
		title(nullable : true)
		description(nullable : true)
	}

	def serialize() {
		[
			'id' : id,
			'merchant' : [
				id : merchant.id
			],
			'name' : name,
			'title' : title,
			'description' : description,
			'is_default' : isDefault,
			'metadata' : metadata ? JSON.parse(metadata) : [:]
		]
	}

}
