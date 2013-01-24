package com.billingstack

class PlanProductRuleRange extends BillingEntity {

	Merchant merchant
	Plan plan
	Product product
	PlanProductRule rule
	BigDecimal valueFrom
	BigDecimal valueTo
	BigDecimal price

	static constraints = {
		rule()
		valueFrom nullable : true
		valueTo nullable : true
		price()
	}

	def serialize() {
		[
			id : id,
			rule : [id : rule.id],
			from : valueFrom,
			to : valueTo,
			price : price
		]
	}
}
