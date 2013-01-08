package com.billingstack

class Merchant {

	String id
	String name

	String language = "EN"
	String currency = "USD"
	
	Date dateCreated
    Date lastUpdated

    Boolean deleted = Boolean.FALSE

	static constraints = {

	}
	
	static mapping = {
		id generator : "uuid", type : "string"
	}

	def serialize() {
		def json = [
			'id' : id,
			'name' : name,
			'language' : language,
			'currency' : currency
		]
		json
	}
}
