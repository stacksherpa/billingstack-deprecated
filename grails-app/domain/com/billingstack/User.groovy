package com.billingstack

class User extends BillingEntity {

	String username
	String password

    String language
    String currency

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
        currency(nullable : true)

    	apiKey(nullable : true)
    	apiSecret(nullable : true)

        contactInformation()
    }

}
