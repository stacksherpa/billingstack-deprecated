package com.billingstack

class Usage {

	Subscription subscription
	Product product
	
	BigDecimal duration
	BigDecimal max
	BigDecimal sum

	Date startTimestamp
	Date endTimestamp
	
	static belongsTo = [
		subscription : Subscription
	]

	static constraints = {
		subscription()
		product()
		max(nullable : true)
		sum(nullable : true)
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
			'duration' : duration,
			'max' : max,
			'sum' : sum,
			'start_timestamp' : startTimestamp,
			'end_timestamp' : endTimestamp
		]
	}

}
