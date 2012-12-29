package com.billingstack

class UsersService {

	def create(merchant, json) {
  	if (!json.username) {	
  		throw new RuntimeException("Username is mandatory")  
  	}
    def user = new User()
    user.merchant = Merchant.load(merchant)
    user.username = json.username
    user.password = json.password
    user.language = json.language
    user.currency = json.currency ?: "USD"
  	if(json.contact_information) {
  		user.contactInformation.email = json.contact_information.email
  	    user.contactInformation.firstName = json.contact_information.first_name
  	    user.contactInformation.lastName = json.contact_information.last_name
  	    user.contactInformation.company = json.contact_information.company
  	    user.contactInformation.phone = json.contact_information.phone
  	    user.contactInformation.address1 = json.contact_information.address_1
  	    user.contactInformation.address2 = json.contact_information.address_2
  	    user.contactInformation.city = json.contact_information.city
  	    user.contactInformation.state = json.contact_information.state
  	    user.contactInformation.zip = json.contact_information.zip
  	    user.contactInformation.country = json.contact_information.country
  	}
    user.apiKey = (UUID.randomUUID() as String).replaceAll('-','').substring(10)
    user.apiSecret = (UUID.randomUUID() as String).replaceAll('-','').substring(6)
    user.save(failOnError : true)
  }
}
