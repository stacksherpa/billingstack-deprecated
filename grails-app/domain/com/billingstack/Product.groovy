package com.billingstack

class Product extends BillingEntity {

	Merchant merchant
	
	String source
	
	String name
	String type
	String measure
	String title
	String description
	
	BigDecimal price
	
	static belongsTo = [
		merchant : Merchant
	]

	static constraints = {
		merchant(unique : 'name')
		source(nullable : true)
		name()
		type(nullable : true)
		measure(nullable : true)
		title(nullable : true)
		description(nullable : true)
		price(nullable : true)
	}

	def serialize() {
		[
			'id' : id,
			'merchant' : [
				'id' : merchant.id
			],
			'source' : source,
			'name' : name,
			'type' : type,
			'measure' : measure,
			'title' : title,
			'description' : description
		]
	}

}
