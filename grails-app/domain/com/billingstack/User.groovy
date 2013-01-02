package com.billingstack

class User extends BillingEntity {

    String username
    String password

    String language
    String currency = "USD"

    String apiKey
    String apiSecret

    static constraints = {
    	username(unique : true)
    	password(nullable : true)

        language(nullable : true)
        currency()

    	apiKey(nullable : true)
    	apiSecret(nullable : true)

    }

    def serialize(detail) {
        def json = [
            'id' : id,
            'username' : username,
            'language' : language,
            'currency' : currency,
            'contact_information' : ContactInfomation.findByUser(this).serialize()
        ]
        if(detail) {
            json.api_key = apiKey
            json.api_secret = apiSecret
        }
        json
    }

}
