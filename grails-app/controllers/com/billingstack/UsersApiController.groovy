package com.billingstack

import grails.converters.JSON

class UsersApiController {
  
  def usersService
  
  def create(String merchant, String customer) {
      try {
          def instance = usersService.create(merchant, customer, request.JSON)
          render instance.serialize() as JSON
      } catch(e) {
          response.status = 500
          def error = ["error":e.message]
          render error as JSON
          return
      }
  }

  def list(String merchant, String customer) { 
    try {
      def query = ['merchant.id':merchant]
      if(customer) {
        query['customer.id'] = customer
      } else {
				query['customer'] = null
			}
      render User.findAllWhere(query).collect { it.serialize() } as JSON
    } catch(e) {
      log.error(e.message, e)
      response.status = 500
      def error = ["error":e.message]
      render error as JSON
      return
    }
  }
  
  def show(String merchant, String customer, String id) { 
    try {
      def user = User.get(id)
			if (user) {
				render user.serialize() as JSON
			} else {
				response.status = 404
        def error = ["error":"User not found"]
        render error as JSON
			}
    } catch(e) {
      log.error(e.message, e)
      response.status = 500
      def error = ["error":e.message]
      render error as JSON
      return
    }
  }

}
