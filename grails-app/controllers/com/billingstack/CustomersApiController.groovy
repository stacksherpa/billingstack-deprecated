package com.billingstack

import grails.converters.JSON

class CustomersApiController {

    def customersService

    def list() {
        try {
            render customersService.findAllWhere(params).collect { it.serialize() } as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def create(String merchant) {
        try {
            render customersService.create(merchant, request.JSON).serialize() as JSON
        } catch(e) {
			log.error(e.message,e)
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
       
    }

    def show(String merchant, String id) {
        try {
            render customersService.show(id).serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
        
    }

    def update(String merchant, String id) {
        try {
            render customersService.update(id, request.JSON).serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def delete(String merchant, String id) {
        try {
            customersService.delete(id)
            render(status : 204)
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }

    }

}
