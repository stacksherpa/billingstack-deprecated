package com.billingstack

import grails.converters.JSON

class PlansApiController {

    def list() {
        try {
			def query = [:]
	        if(params.merchant) {
	            query['merchant.id'] = params.merchant
	        }
            render Plan.findAllWhere(query).collect { it.serialize() } as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    }

    def create(String merchant) {
        try {
            def json = request.JSON
            def plan = new Plan(
                merchant : Merchant.load(merchant),
                name : json.name,
                title : json.title,
                description : json.description
            )
			if(json.quotas) {
                plan.quotas = json.quotas.toString()
			}
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
                    def planProduct = new PlanProduct(product : product)
					if(current.rules) {
						current.rules.each { rule ->
				            def planProductRule = new PlanProductRule(type : rule.type)
				            if(rule.type == 'fixed') {
				                planProductRule.price = rule.price
				            } else if(rule.type == 'volume-range') {
				                rule.ranges.each { range ->
				                    planProductRule.addToRanges(
				                        valueFrom : range.from,
				                        valueTo : range.to,
				                        price : range.price
				                    )
				                }
				            }
				            planProduct.addToRules(planProductRule)
				        }
					}
			        
			        plan.addToProducts(planProduct)
                }
            }
            plan.save(flush: true, failOnError : true)
            render plan.serialize() as JSON
        } catch (e) {
            response.status = 500
            def error = ["error":e.message]
            render error  as JSON
            return
        }  
    }

    def show(String merchant, String id) {
        try {
            render Plan.get(id).serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def update(String merchant, String id) { 
        try {
            render Plan.get(id).serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def delete(String merchant, String id) {
        try {
            def instance = Plan.load(id)
            instance.delete(flush : true)
            render(status : 204)
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
        
    }

}
