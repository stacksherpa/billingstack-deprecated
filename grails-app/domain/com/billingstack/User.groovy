package com.billingstack

class User extends BillingEntity {

    Merchant merchant

	String username
	String password

    String language
    String currency = "USD"

	String apiKey
	String apiSecret

    ContactInformation contactInformation = new ContactInformation()

    static belongsTo = [
        merchant : Merchant
    ]

    static hasOne = [
        contactInformation : ContactInformation
    ]

    static hasMany = [
        userRoles : UserRole
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

    def serialize(detail) {
        def json = [
            'id' : id,
            'username' : username,
            'language' : language,
            'currency' : currency,
            'contact_information' : contactInformation.serialize()
        ]
        if(detail) {
            json.api_key = apiKey
            json.api_secret = apiSecret
        }
        json
    }

}
