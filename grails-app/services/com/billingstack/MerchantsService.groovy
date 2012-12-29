package com.billingstack

class MerchantsService {

  def usersService

  def list(filters) { 
  	Merchant.list(filters)
  }

  def create(json) {
    def merchant = Merchant.newInstance(
      name : json.name
    ).save(flush : true, failOnError: true)
    UserRole.newInstance(
      user : usersService.create(
        merchant.id, 
        json.user
      ),
      role : Role.findByName("MERCHANT_ADMIN")
    ).save(failOnError: true)
    merchant
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
      def user = Merchant.load(id)
      user.delete(flush : true)
  }
  
}
  