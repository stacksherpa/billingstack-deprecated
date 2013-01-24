package com.billingstack

class PlansService {

		def create(String merchant, json) {
			def plan = new Plan(
				merchant : Merchant.load(merchant),
				name : json.name,
				title : json.title,
				description : json.description,
				provider : json.provider
			)
			if(json.metadata) {
				plan.metadata = json.metadata.toString()
			}
			plan.save(failOnError : true)
			if(json.products) {
				products(plan, json.products)
			}
			plan
		}

		def show(String id) { 
			Plan.get(id)
		}
		
		def update(String merchant, String id, json) {
			
			def plan = Plan.get(id)
			plan.name = json.name
			plan.title = json.title
			plan.provider = json.provider
			plan.description = json.description
			
			if(json.metadata) {
				plan.metadata = json.metadata.toString()
			}
			
			PlanProductRuleRange.executeUpdate "DELETE FROM PlanProductRuleRange WHERE plan.id = :id", [id: id]
			PlanProductRule.executeUpdate "DELETE FROM PlanProductRule WHERE plan.id = :id", [id: id]
			PlanProduct.executeUpdate "DELETE FROM PlanProduct WHERE plan.id = :id", [id: id]
			
			if(json.products) {
				products(plan, json.products)
			}
			plan.save()
		}

		def delete(String id) {
			PlanProductRuleRange.executeUpdate "DELETE FROM PlanProductRuleRange WHERE plan.id = :id", [id: id]
			PlanProductRule.executeUpdate "DELETE FROM PlanProductRule WHERE plan.id = :id", [id: id]
			PlanProduct.executeUpdate "DELETE FROM PlanProduct WHERE plan.id = :id", [id: id]
			Plan.load(id).delete(flush : true)
		}
		
		def products(plan, products) {
			products.each { current ->
				def product 
				if(current.id) {
						product = Product.load(current.id)
				} else if (current.name) {
						product = Product.findWhere(
								'merchant.id' : plan.merchant.id,
								name : current.name
						)
				}
				def planProduct = PlanProduct.newInstance(merchant : plan.merchant, plan : plan, product : product).save()
				if(current.rules) {
					current.rules.each { rule ->
						def planProductRule = new PlanProductRule(merchant : plan.merchant, plan : plan, product : product, type : rule.type)
						if(rule.type == 'fixed') {
								planProductRule.price = rule.price
								planProductRule.save()
						} else if(rule.type == 'volume-range') {
								planProductRule.save()
								rule.ranges.each { range ->
										def planProductRuleRange = new PlanProductRuleRange(
											merchant : plan.merchant,
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
}
