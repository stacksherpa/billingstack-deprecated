package com.billingstack

import grails.converters.JSON

class UserRolesApiController {

    def create(String merchant, String customer, String user, String id) { 
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

    def delete(String merchant, String customer, String user, String id) { 
    	UserRole.where {
    		owner.merchant.id == merchant
    		owner.customer.id == customer
    		owner.user.id == user
    		owner.role.id == id
    	}.deleteAll()
    	render(status : 204)
    }

}
