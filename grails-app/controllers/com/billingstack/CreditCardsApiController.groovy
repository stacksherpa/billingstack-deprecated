package com.billingstack

import grails.converters.JSON

class CreditCardsApiController {

    def paymentGatewaysService

    def list(String merchant, String customer) {
        try {
            def result = paymentGatewaysService.load(merchant).listCreditCards([
                customer : [
                    id : customer
                ]
            ])
            render result as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e as String]
            render error as JSON
            return
        }
        
    }

    def create(String merchant, String customer) {
        def result = paymentGatewaysService.load(merchant).createCreditCard(customer, request.JSON)
        render result as JSON
    }

    def delete(String merchant, String customer, String id) {
        //def result = paymentGatewaysService.load(merchant).createCreditCard(customer, json)
        response.status = 500
	    render ""
    }
}
