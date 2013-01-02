package com.billingstack

class Role {

		String name

    static constraints = {
    	name(unique : true)
    }

    def serialize() {
			[
				'id' : id,
				'name' : name,
			]
		}
}
