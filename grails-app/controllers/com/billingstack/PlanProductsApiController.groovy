package com.billingstack

import grails.converters.JSON

class PlanProductsApiController {

    def list() {
        def query = ['plan.id' : params.plan]
        if(params.product) {
            query['product.id'] = params.product
        }
    	def items = PlanProduct.findAllWhere(query)
    	render items.collect { it.serialize() } as JSON
    }

    def show(String merchant, String plan, String product) { 
    	def instance = PlanProduct.findWhere('plan.id' : plan, 'product.id' : product)
        if(instance) {
            render instance.serialize() as JSON
        } else {
            def error = ["error":"plan item not found"]
            render error as JSON
            return
        }
    }

    def update(String merchant, String plan, String product) { 
    	def json = request.JSON
        def planProduct = PlanProduct.findOrCreateWhere('plan.id' : plan, 'product.id' : product)
        json.rules.each { rule ->
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
		def planInstance = Plan.get(plan)
        planInstance.addToProducts(planProduct)
        if (!planInstance.save(flush: true, failOnError : true)) {
            def error = ["error":""]
            render error as JSON
            return
        } 
        render planProduct.serialize() as JSON
    }

    def delete(String merchant, String plan, String product) {
        def instance = PlanProduct.findWhere('plan.id' : plan, 'product.id' : product)
        if (!instance) {
            render(status: 404, model : ["error":message(code: 'default.not.found.message', args: [message(code: 'planProduct.label', default: 'Plan Product'), id])]) as JSON
            return
        }
        instance.delete(flush : true)
        render(status : 204)
    }

}
