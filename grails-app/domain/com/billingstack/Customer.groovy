package com.billingstack

class Customer extends User {

	Merchant merchant
	
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

  def serialize() {
  	[
  		'id' : id,
  		'merchant' : [
  			id : merchant.id
  		],
  		'username' : username,
  		'language' : language,
  		'currency' : currency,
  		'contact_information' : contactInformation.serialize()
		]
  }
}
