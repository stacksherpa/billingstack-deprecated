package com.billingstack

class Role {

		String name

    static constraints = {
    	name(unique : true)
    }
}
