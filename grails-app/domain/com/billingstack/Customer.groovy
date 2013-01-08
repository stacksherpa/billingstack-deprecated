package com.billingstack

class Customer {

	Merchant merchant
	
	String id
	String name

	String language = "EN"
	String currency = "USD"
	
	Date dateCreated
    Date lastUpdated

    Boolean deleted = Boolean.FALSE

	static constraints = {
		merchant(unique : "name")
	}

	static mapping = {
		id generator : "uuid", type : "string"
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
