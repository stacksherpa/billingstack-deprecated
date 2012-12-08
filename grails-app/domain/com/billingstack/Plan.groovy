package com.billingstack

import grails.converters.JSON

class Plan {
	
	String id

	Merchant merchant

	String name
	String title
	String description
	
	String metadata
	
	Date dateCreated
	Date lastUpdated
	
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
    	metadata nullable : true
  }

  static mapping = {
	id generator : "uuid", type : "string"
	metadata type: 'text'
  }

  def serialize(plan) {
    [
        'id' : id,
        'name' : name,
        'title' : title,
        'description' : description,
        'products' : products.collect { it.serialize() },
        'metadata' : metadata ? JSON.parse(metadata) : [:]
    ]
  }

}

