package com.billingstack

class PlansService {

    def create(String merchant, json) {
    	def merchantRef = Merchant.load(merchant)
    	def plan = new Plan(
        merchant : merchantRef,
        name : json.name,
        title : json.title,
        description : json.description
      )
			if(json.metadata) {
      	plan.metadata = json.metadata.toString()
			}
			plan.save()
      if(json.products) {
	      json.products.each { current ->
	        def product 
	        if(current.id) {
	            product = Product.load(current.id)
	        } else if (current.name) {
	            product = Product.findWhere(
	                'merchant.id' : merchant,
	                name : current.name
	            )
	        }
	      	def planProduct = PlanProduct.newInstance(merchant : merchantRef, plan : plan, product : product).save()
					if(current.rules) {
						current.rules.each { rule ->
	            def planProductRule = new PlanProductRule(merchant : merchantRef, plan : plan, product : planProduct, type : rule.type)
	            if(rule.type == 'fixed') {
	                planProductRule.price = rule.price
	                planProductRule.save()
	            } else if(rule.type == 'volume-range') {
	            		planProductRule.save()
	                rule.ranges.each { range ->
	                		def planProductRuleRange = new PlanProductRuleRange(
	                			merchant : merchantRef,
	                			plan : plan,
	                			rule : planProductRule,
	                			valueFrom : range.from,
	                      valueTo : range.to,
	                      price : range.price
	                		).save()
	                }
	            }
				    }
					}
				}
      }
      plan
    }

    def show(String id) { 
    	Plan.get(id)
    }

    def delete(String id) {
    	PlanProductRuleRange.executeUpdate "DELETE FROM PlanProductRuleRange WHERE plan.id = :id", [id: id]
      PlanProductRule.executeUpdate "DELETE FROM PlanProductRule WHERE plan.id = :id", [id: id]
      PlanProduct.executeUpdate "DELETE FROM PlanProduct WHERE plan.id = :id", [id: id]
      Plan.load(id).delete(flush : true)
    }
}
