package com.billingstack

import grails.converters.JSON

class CustomersService {

    def applicationService

    def paymentGatewaysService

    def findAllWhere(filters) {
		def query = [:]
        if(filters.merchant) {
            query['merchant.id'] = filters.merchant
        }
    	Customer.findAllWhere(query)
    }

    def create(String merchant, json) {
        try {
            def instance = new Customer()
			instance.merchant = Merchant.get(merchant)
            instance.username = json.username
            instance.password = json.password
            instance.language = json.language
            instance.currency = json.currency ?: instance.merchant.currency
			if(json.contact_information) {
				instance.contactInformation.email = json.contact_information.email
	            instance.contactInformation.firstName = json.contact_information.first_name
	            instance.contactInformation.lastName = json.contact_information.last_name
	            instance.contactInformation.company = json.contact_information.company
	            instance.contactInformation.phone = json.contact_information.phone
	            instance.contactInformation.address1 = json.contact_information.address_1
	            instance.contactInformation.address2 = json.contact_information.address_2
	            instance.contactInformation.city = json.contact_information.city
	            instance.contactInformation.state = json.contact_information.state
	            instance.contactInformation.zip = json.contact_information.zip
	            instance.contactInformation.country = json.contact_information.country
			}
            instance.apiKey = (UUID.randomUUID() as String).replaceAll('-','').substring(10)
            instance.apiSecret = (UUID.randomUUID() as String).replaceAll('-','').substring(6)
			instance.save(flush : true, failOnError : true)
			def paymentGateway = paymentGatewaysService.load(merchant)
            paymentGateway.createAccount([account : instance])
            instance
        } catch (e) {
            println e.message
            throw new RuntimeException(e.message)
        }
    }

    def show(String id) { 
     	Customer.get(id)
    }

    def update(String id, json) { 
    	def account = Customer.get(id)
    	account.properties = json
    	account
    }

    def delete(String id) {
        def instance = Customer.load(id)
        instance.delete(flush : true)
    }
    
}
