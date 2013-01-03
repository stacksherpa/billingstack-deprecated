package com.billingstack

import grails.converters.JSON

class UsersApiController {

	def list(String merchant, String customer) { 
		try {
			def query = ['merchant.id':merchant]
			if(customer) {
				query['customer.id'] = customer
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
					render ([
						user : user,
						roles : roles
					]) as JSON
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
