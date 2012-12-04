package com.billingstack

import grails.converters.JSON

class Plan extends BillingEntity {

	Merchant merchant

	String name
	String title
	String description

	String quotas

	List products = []
	
	Set subscriptions = [] as Set

	static belongsTo = [
	    merchant : Merchant
	]

	static hasMany = [
		products : PlanProduct,
		subscriptions : Subscription
	]

  static constraints = {
		merchant(unique : 'name')
		title nullable : true
		description nullable : true
    	quotas nullable : true
  }

  static mapping = {
    quotas type: 'text'
  }

  def serialize(plan) {
    [
        'id' : id,
        'merchant' : [
          'id' : id
        ],
        'name' : name,
        'title' : title,
        'description' : description,
        'products' : products.collect { planProduct ->
            planProduct.serialize()
        },
        'quotas' : quotas ? JSON.parse(quotas) : [:]
    ]
  }

}

