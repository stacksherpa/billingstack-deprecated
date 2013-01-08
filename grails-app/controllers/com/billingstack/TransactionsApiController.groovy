package com.billingstack

import grails.converters.JSON

class TransactionsApiController {

    def transactionsService

    def list() {
        render transactionsService.list(params) as JSON
    }

    def create(String merchant, String customer) {
    	render transactionsService.create(
            merchant,
            customer,
            request.JSON
        ) as JSON
    }

    def show(String merchant, String customer, String id) { 
    	render transactionsService.showTransaction(
            merchant,
            customer,
            request.JSON
        ) as JSON
    }

    def action(String merchant, String customer, String transaction) {
        def action = request.JSON.entrySet().iterator().next()
        transactionsService.action(merchant, transaction, action.key, action.value)
        render [:] as JSON
    }

}
