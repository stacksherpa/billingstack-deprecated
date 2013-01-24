package com.billingstack

class Metadata {
	
		BillingEntity entity
		
		String key
		String value

    static constraints = {
			entity(unique : 'key')
			key()
			value(nullable : true)
    }
}
