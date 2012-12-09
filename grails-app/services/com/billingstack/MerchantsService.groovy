package com.billingstack

import com.billingstack.exceptions.JSONObjectException

class MerchantsService {

  def list(filters) { 
  	Merchant.list(filters)
  }

  def create(json) throws JSONObjectException {
	if (json.username==null) {	
		throw new JSONObjectException("Username is mandatory")  
	}
    def instance = new Merchant()
    instance.username = json.username
    instance.password = json.password
    instance.language = json.language
    instance.currency = json.currency ?: "USD"
	if(json.contact_information) {
		instance.contactInformation.email = json.contact_information.email
	    instance.contactInformation.firstName = json.contact_information.first_name
	    instance.contactInformation.lastName = json.contact_information.last_name
	    instance.contactInformation.company = json.contact_information.company
	    instance.contactInformation.phone = json.contact_information.phone
	    instance.contactInformation.address1 = json.contact_information.address_1
	    instance.contactInformation.address2 = json.contact_information.address_2
	    instance.contactInformation.city = json.contact_information.city
	    instance.contactInformation.state = json.contact_information.state
	    instance.contactInformation.zip = json.contact_information.zip
	    instance.contactInformation.country = json.contact_information.country
	}
    instance.apiKey = (UUID.randomUUID() as String).replaceAll('-','').substring(10)
    instance.apiSecret = (UUID.randomUUID() as String).replaceAll('-','').substring(6)
    instance.save(flush : true, failOnError : true)
    instance
    
  }

  def show(String id) {
      Merchant.get(id)
  }

  def update(String id, json) { 
  	def merchant = Merchant.get(id)
  	merchant.properties = json
 		merchant
  }

  def delete(String id) {
      def instance = Merchant.load(id)
      instance.delete(flush : true)
  }
  
}
  