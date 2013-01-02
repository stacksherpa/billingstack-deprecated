package com.billingstack

class Customer extends BillingEntity {

	Merchant merchant

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
