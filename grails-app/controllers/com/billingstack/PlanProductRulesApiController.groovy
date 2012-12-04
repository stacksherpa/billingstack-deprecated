package com.billingstack

import grails.converters.JSON

class PlanProductRulesApiController {

    def list() {
      try {
        def query = [
          'plan.id' : params.plan,
          'item.id' : params.item
        ]
        def items = PlanProductRule.findAllWhere(query)
        render items.collect { it.serialize() } as JSON
      } catch(e) {
        response.status = 500
        def error = ["error":e.message]
        render error as JSON
      }

    }

    def create(String merchant, String plan, String product) {
      try {
        def json = request.JSON
      
        def planProduct = PlanProduct.findWhere('plan.id' : plan, 'product.id' : product)
        
        def planProductRule = new PlanProductRule(type : json.type)
        if(json.type == 'fixed') {
            planProductRule.price = json.price
        } else if(json.type == 'volume-range') {
            json.ranges.each { range ->
                planProductRule.addToRanges(
                    valueFrom : range.from as BigDecimal,
                    valueTo : range.to as BigDecimal,
                    price : range.price
                )
            }
        }
        planProduct.addToRules(planProductRule)
        if (!planProduct.save(flush: true, failOnError : true)) {
            def error = ["error":""]
            render error as JSON
            return
        } 
        render planProductRule.serialize() as JSON
      } catch(e) {
        response.status = 500
        def error = ["error":e.message]
        render error as JSON
      }

    }

    def delete(String merchant, String plan, String product, String id) {
      try {
        def instance = PlanProductRule.load(id)
        instance.delete(flush : true)
        render(status : 204)
      } catch(e) {
        response.status = 500
        def error = ["error":e.message]
        render error as JSON
      }
    }
}
