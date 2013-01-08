package com.billingstack

class PlanProductRuleRange extends BillingEntity {

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

	public Merchant getMerchant(){
		return rule.getMerchant()
	}
	
	public Plan getPlan() {
		return rule.getPlan()
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
