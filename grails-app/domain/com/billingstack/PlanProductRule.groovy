package com.billingstack

class PlanProductRule extends BillingEntity {

	PlanProduct product
	String type
	BigDecimal price

    static constraints = {
    	product()
    	type()
    	price(nullable : true)
    }

	public Merchant getMerchant(){
		return product.getMerchant()
	}
	
	public Plan getPlan() {
		return product.getPlan()
	}
	
	def findAllRuleRanges() {
		PlanProductRuleRange?.findAllByRule(this)
	}
	
    def serialize() {
		def json = [id : id, type: type]
		if(type == 'fixed') {
		  json.price = price
		} else if (type == 'volume-range'){
		    json.ranges = findAllRuleRanges().collect { range ->
		      range.serialize()
		    }
		}
		json
    }
}
