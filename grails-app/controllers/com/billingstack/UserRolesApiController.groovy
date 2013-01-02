package com.billingstack

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
			ur.save()
    }

    def delete(String merchant, String customer, String user, String id) { 
    	UserRole.where {
    		eq 'merchant.id' : merchant
    		eq 'customer.id' : customer
    		eq 'user.id' : user
    		eq 'role.id' : id 
    	}.deleteAll()
    }

}
