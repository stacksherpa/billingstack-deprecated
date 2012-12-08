package com.billingstack

import grails.converters.JSON

class Subscription extends BillingEntity {
	
	Merchant merchant

	Customer customer

	Plan plan

	String paymentMethod

	Integer billingDay
	
	String provider
	String resource
	
	Set usages = [] as Set
	
	static belongsTo = [
		merchant : Merchant,
		customer : Customer,
		plan : Plan
	]
	
	static hasMany = [
		usages : Usage
	]

	static constraints = {
		paymentMethod nullable : true
		billingDay nullable : true
		provider nullable : true
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
			'provider' : provider,
			'resource' : resource
		]
	}

}


