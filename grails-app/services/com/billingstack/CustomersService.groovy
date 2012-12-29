package com.billingstack

import grails.converters.JSON

class CustomersService {

    def applicationService

    def paymentGatewaysService

    def usersService

    def findAllWhere(filters) {
		def query = [:]
        if(filters.merchant) {
            query['merchant.id'] = filters.merchant
        }
    	Customer.findAllWhere(query)
    }

    def create(String merchant, json) {
        try {
            def customer = new Customer(
                merchant : Merchant.load(merchant),
                name : json.name
            ).save(flush : true, failOnError : true)
            UserRole.newInstance(
                user : usersService.create(
                    merchant, 
                    json.user
                ),
                role : Role.findByName("CUSTOMER_ADMIN"),
                customer : customer
            ).save(failOnError: true)
			//def paymentGateway = paymentGatewaysService.load(merchant)
            //paymentGateway.createAccount([account : instance])
            customer
        } catch (e) {
            log.error(e.message,e)
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
