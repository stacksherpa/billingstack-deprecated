package com.billingstack

import grails.converters.JSON

class UsersApiController {

    def list() { 
    	try {
				def query = [:]
	      if(params.merchant) {
	      	query['merchant.id'] = params.merchant
	      }
        render User.list().collect { it.serialize() } as JSON
      } catch(e) {
          response.status = 500
          def error = ["error":e.message]
          render error as JSON
          return
      }
    }
}
