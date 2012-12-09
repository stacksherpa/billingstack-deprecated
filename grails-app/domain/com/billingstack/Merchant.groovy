package com.billingstack

class Merchant extends User {

	Set products
	Set customers
	Set subscriptions
	Set invoices
	Set paymentGateways

	static hasMany = [
		products : Product,
		customers : Customer,
		subscriptions : Subscription,
		paymentGateways : PaymentGateway,
	]

	static constraints = { customers() }

	def serialize(detail) {
		def json = [
					'id' : id,
					'username' : username,
					'language' : language,
					'currency' : currency,
					'contact_information' : contactInformation.serialize()
				]
		if(detail) {
			json.api_key = apiKey
			json.api_secret = apiSecret
		}
		json
	}
}
