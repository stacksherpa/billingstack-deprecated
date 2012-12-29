package com.billingstack

class UserRole {

		User user

		Role role

		Customer customer

		static belongsTo = [
			user : User,
			role : Role
		]

    static constraints = {
    	customer(nullable : true)
    }
}
