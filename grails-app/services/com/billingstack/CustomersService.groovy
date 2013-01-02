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
            def merchantRef = Merchant.get(merchant)
            def customer = new Customer(
                merchant : merchantRef,
                name : json.name,
                currency : json.currency ?: merchantRef.currency,
                language : json.language ?: merchantRef.language
            ).save(flush : true, failOnError : true)
            UserRole.newInstance(
                user : usersService.create(json.user),
                merchant : merchantRef,
                customer : customer,
                role : Role.findByName("CUSTOMER_ADMIN"),
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
      UserRole.executeUpdate "DELETE FROM UserRole WHERE customer.id = :id", [id: id]
      InvoiceLine.executeUpdate "DELETE FROM InvoiceLine WHERE customer.id = :id", [id: id]
      Invoice.executeUpdate "DELETE FROM Invoice WHERE customer.id = :id", [id: id]
      Usage.executeUpdate "DELETE FROM Usage WHERE customer.id = :id", [id: id]
      Subscription.executeUpdate "DELETE FROM Subscription WHERE customer.id = :id", [id: id]
      Customer.load(id).delete(flush : true)
    }
    
}
