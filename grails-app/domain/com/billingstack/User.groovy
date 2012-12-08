package com.billingstack

class User extends BillingEntity {

	String username
	String password

    String language
    String currency = "USD"

	String apiKey
	String apiSecret

    ContactInformation contactInformation = new ContactInformation()

    static hasOne = [
        contactInformation : ContactInformation
    ]

    static constraints = {
    	username(unique : true)
    	password(nullable : true)

        language(nullable : true)
        currency()

    	apiKey(nullable : true)
    	apiSecret(nullable : true)

        contactInformation()
    }

}
