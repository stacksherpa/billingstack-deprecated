package com.billingstack

import grails.converters.JSON

class Subscription extends BillingEntity {
	
	Merchant merchant

	Customer customer

	Plan plan

	String paymentMethod

	Integer billingDay
	
	String resource

	static constraints = {
		paymentMethod nullable : true
		billingDay nullable : true
		resource nullable : true
	}

	def serialize() {
		[
			'id' : id,
			'customer' : [
				'id' : customer.id
			],
			'plan' : [
				'id' : plan.id
			],
			'billing_day' : billingDay,
			'resource' : resource
		]
	}

}


