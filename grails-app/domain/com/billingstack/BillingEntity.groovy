package com.billingstack

class BillingEntity {

	String id

	Date dateCreated
	Date lastUpdated

	Boolean deleted = Boolean.FALSE
	
	Map metadata = [:]

    static constraints = {
    	id()
    	dateCreated()
    	lastUpdated()
    	deleted()
    }

    static mapping = {
    	id generator : "uuid", type : "string"
        tablePerHierarchy false
    }
}
