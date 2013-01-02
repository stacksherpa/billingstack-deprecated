package com.billingstack

class Customer extends BillingEntity {

	Merchant merchant

	String name

	String language = "EN"
  String currency = "USD"
	
 	static constraints = {
  }

  def serialize(detail) {
		def json = [
			'id' : id,
			'name' : name,
			'language' : language,
			'currency' : currency
		]
		json
	}
}
