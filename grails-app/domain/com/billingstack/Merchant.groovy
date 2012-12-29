package com.billingstack

class Merchant extends BillingEntity {

	String name

	Set users
	Set products
	Set customers
	Set subscriptions
	Set invoices
	Set paymentGateways

	static hasMany = [
		users : User,
		products : Product,
		customers : Customer,
		subscriptions : Subscription,
		paymentGateways : PaymentGateway,
	]

	static constraints = { customers() }

	def serialize(detail) {
		def json = [
			'id' : id,
			'name' : name,
		]
		json
	}
}
