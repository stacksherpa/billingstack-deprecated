package com.billingstack

class Customer extends BillingEntity {

	Merchant merchant

	String name
	
	Set subscriptions
	
	Set invoices
	
	static belongsTo = [
		merchant : Merchant
	]
	
	static hasMany = [
		subscriptions : Subscription,
		invoices : Invoice
	]
	
 	static constraints = {
 		merchant()
		subscriptions()
		invoices()
  }

  def serialize(detail) {
		def json = [
			'id' : id,
			'name' : name,
		]
		json
	}
}
