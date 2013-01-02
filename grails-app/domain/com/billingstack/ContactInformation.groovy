package com.billingstack

import grails.converters.JSON

class ContactInformation extends BillingEntity {

	User user

	String email
	String firstName
	String lastName
	
	String company
	String phone
	String address1
	String address2
	String city
	String state
	String zip
	String country

	static constraints = {
		email(nullable : true)
		firstName(nullable : true)
		lastName(nullable : true)
		company(nullable : true)
		phone(nullable : true)
		address1(nullable : true)
		address2(nullable : true)
		city(nullable : true)
		state(nullable : true)
		zip(nullable : true)
		country(nullable : true)		
	}

	def serialize() {
  	[
  		'email' : email,
  		'first_name' : firstName,
  		'last_name' : lastName,
  		'company' : company,
  		'phone' : phone,
  		'address_1' : address1,
  		'address_2' : address2,
  		'city' : city,
  		'state' : state,
  		'zip' : zip,
  		'country' : country
		]
  }

}