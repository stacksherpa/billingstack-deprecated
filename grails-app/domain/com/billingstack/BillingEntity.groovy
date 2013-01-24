package com.billingstack

class BillingEntity {

    String id

    Date dateCreated
    Date lastUpdated

		String metadata

    Boolean deleted = Boolean.FALSE

    static constraints = {
      id()
      dateCreated()
      lastUpdated()
			metadata(nullable : true)
      deleted()
    }

    static mapping = {
      id generator : "uuid", type : "string"
			metadata type: 'text'
      tablePerHierarchy false
    }
    
}
