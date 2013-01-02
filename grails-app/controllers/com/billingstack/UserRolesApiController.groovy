package com.billingstack

import grails.converters.JSON

class UserRolesApiController {

    def create(String merchant, String customer, String user, Long id) { 
    	def ur = UserRole.newInstance(
    		merchant : Merchant.load(merchant),
    		user : User.load(user),
    		role : Role.load(id)
			)
			if(customer) {
				ur.customer = Customer.load(customer)
			}
			render ur.save() as JSON
    }

    def delete(String merchant, String customer, String user, Long id) {
    	def query = [
    		'merchant.id' : merchant,
    		'user.id' : user,
    		'role.id' : id
    	]
    	if(customer) {
    		query['customer.id'] = customer
    	}
    	UserRole.findWhere(query).delete(flush : true)
    	render(status : 204)
    }

}
