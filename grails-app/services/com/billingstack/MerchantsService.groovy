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
      user : usersService.create(json.user),
      merchant : merchant,
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
      UserRole.executeUpdate "DELETE FROM UserRole WHERE merchant.id = :id", [id: id]
      InvoiceLine.executeUpdate "DELETE FROM InvoiceLine WHERE merchant.id = :id", [id: id]
      Invoice.executeUpdate "DELETE FROM Invoice WHERE merchant.id = :id", [id: id]
      Usage.executeUpdate "DELETE FROM Usage WHERE merchant.id = :id", [id: id]
      Subscription.executeUpdate "DELETE FROM Subscription WHERE merchant.id = :id", [id: id]
      Customer.executeUpdate "DELETE FROM Customer WHERE merchant.id = :id", [id: id]
      PlanProductRuleRange.executeUpdate "DELETE FROM PlanProductRuleRange WHERE merchant.id = :id", [id: id]
      PlanProductRule.executeUpdate "DELETE FROM PlanProductRule WHERE merchant.id = :id", [id: id]
      PlanProduct.executeUpdate "DELETE FROM PlanProduct WHERE merchant.id = :id", [id: id]
      Plan.executeUpdate "DELETE FROM Plan WHERE merchant.id = :id", [id: id]
      Product.executeUpdate "DELETE FROM Product WHERE merchant.id = :id", [id: id]
      PaymentGateway.executeUpdate "DELETE FROM PaymentGateway WHERE merchant.id = :id", [id: id]
      Merchant.load(id).delete(flush : true)
  }
  
}
  