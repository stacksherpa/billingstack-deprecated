package com.billingstack

import grails.converters.JSON

class UsersApiController {
  
  def usersService
  
  def create() {
      try {
          def instance = usersService.create(request.JSON)
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
      render UserRole.findAllWhere(query).collect { it.user.serialize() } as JSON
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
      if(user) {
        def query = ['merchant.id':merchant, 'user.id' : id]
        if(customer) {
          query['customer.id'] = customer
        }
        def roles = UserRole.findAllWhere(query).collect { it.role.serialize() }
        if(roles) {
          def result = user.serialize()
          result.roles = roles
          render result as JSON
        } else {
          response.status = 404
          def error = ["error":"User not found"]
          render error as JSON
        }
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
