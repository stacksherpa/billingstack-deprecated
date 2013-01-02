package com.billingstack

class UserRole {

		Merchant merchant

		Customer customer

		User user

		Role role

    static constraints = {
    	customer(nullable : true)
    }
}
