package com.billingstack

import grails.converters.JSON

class InvoicesApiController {

	def invoicesService

    def list() {
      try {
            render invoicesService.findAllWhere(params).collect { it.serialize() } as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def create(String merchant, String customer) {
      try {
            def instance = invoicesService.create(merchant, customer, request.JSON)
			render instance.serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
      
    }

    def show(String merchant, String id) { 
      try {
            render invoicesService.show(id).serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def update(String merchant, String id) {
      try {
            render invoicesService.show(id).serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def delete(String merchant, String id) { 
      try {
           invoicesService.delete(id)
      response.status = 204
      render ""
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

}
