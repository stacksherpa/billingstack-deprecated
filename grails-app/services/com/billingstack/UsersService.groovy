package com.billingstack

class UsersService {

	def create(json) {
  	if (!json.username) {	
  		throw new RuntimeException("Username is mandatory")  
  	}
    def user = new User()
    user.username = json.username
    user.password = json.password
    user.language = json.language
    user.apiKey = (UUID.randomUUID() as String).replaceAll('-','').substring(10)
    user.apiSecret = (UUID.randomUUID() as String).replaceAll('-','').substring(6)
    user.save()
  	if(json.contact_information) {
      def contactInformation = new ContactInformation(user : user)
  		contactInformation.email = json.contact_information.email
	    contactInformation.firstName = json.contact_information.first_name
	    contactInformation.lastName = json.contact_information.last_name
	    contactInformation.company = json.contact_information.company
	    contactInformation.phone = json.contact_information.phone
	    contactInformation.address1 = json.contact_information.address_1
	    contactInformation.address2 = json.contact_information.address_2
	    contactInformation.city = json.contact_information.city
	    contactInformation.state = json.contact_information.state
	    contactInformation.zip = json.contact_information.zip
	    contactInformation.country = json.contact_information.country
      contactInformation.save()
  	}
    user
  }

  def delete(String id) {
    UserRole.executeUpdate "DELETE FROM UserRole WHERE user.id = :id", [id: id]
    User.load(id).delete(flush : true)
  }
}
