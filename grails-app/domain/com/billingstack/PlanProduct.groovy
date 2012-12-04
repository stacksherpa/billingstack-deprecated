package com.billingstack

import org.hibernate.proxy.HibernateProxyHelper

class PlanProduct extends BillingEntity {

	Plan plan

	Product product

	List rules = []

	static belongsTo =[
		plan : Plan
	]

	static hasMany = [
		rules : PlanProductRule
	]

    static constraints = {
      plan()
      product()
      rules()
    }

    def serialize() {
      [
          'id' : id,
          'plan' : [id : plan.id],
          'product' : product.serialize(),
          'rules' : rules.collect { rule ->
              rule.serialize()
          }
      ]
    }

}