package com.billingstack

class PlanProductRule extends BillingEntity {

	PlanProduct product

	String type

	BigDecimal price
	
	List ranges

	static belongsTo = [
		product : PlanProduct
	]

	static hasMany = [
		ranges : PlanProductRuleRange
	]

    static constraints = {
    	product()
    	type()
    	price(nullable : true)
    }

    def serialize() {
		def json = [id : id, type: type]
		if(type == 'fixed') {
		  json.price = price
		} else if (type == 'volume-range'){
		    json.ranges = ranges.collect { range ->
		      range.serialize()
		    }
		}
		json
    }
}
