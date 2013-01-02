package com.billingstack

class Usage {

	Merchant merchant

	Customer customer

	Subscription subscription

	Product product
	
	BigDecimal value
	
	String measure
	
	BigDecimal price
	BigDecimal total

	Date startTimestamp
	Date endTimestamp

	static constraints = {
		subscription()
		value(nullable : true)
		measure(nullable : true)
		price(nullable : true)
		total(nullable : true)
		startTimestamp(nullable : true)
		endTimestamp(nullable : true)
	}

	def serialize() {
		[
			'id' : id,
			'subscription' : [
				id : subscription.id
			],
			'product' : [
				id : product.id
			],
			'value' : value,
			'measure' : measure,
			'price' : price,
			'total' : total,
			'start_timestamp' : startTimestamp,
			'end_timestamp' : endTimestamp
		]
	}

}
