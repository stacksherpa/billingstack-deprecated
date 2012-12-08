package com.billingstack

class SubscriptionsService {

    def paymentGatewaysService

    def findAllWhere(filters) {
        def query = [:]
    	def subscriptions
        if(filters.merchant) {
            query['merchant.id'] = filters.merchant   
        }
    	if(filters.customer) {
            query['customer.id'] = filters.customer	
  		}
		Subscription.findAllWhere(query)
    }

    def create(String merchant, String customer, json) {
		def instance = new Subscription()

        //use an existing credit card or create a new one
        if(json.credit_card) {
            def result
            if(json.credit_card.id) {
                instance.paymentMethod = json.credit_card.id
            } else {
                def paymentGateway = paymentGatewaysService.load(merchant)
                instance.paymentMethod = paymentGateway.createCreditCard(customer, json.credit_card).target.token
            }
        }
	    instance.merchant = Merchant.load(merchant)
        instance.customer = Customer.load(customer)
        instance.plan = Plan.load(json.plan.id)
        instance.billingDay = new Date().date
		instance.provider = json.provider
		instance.resource = json.resource
        instance.save(flush : true, failOnError : true)
    }

    def show(String id) { 
    	Subscription.get(id)
    }

    def update(String id) { 
    	Subscription.get(id)
    }

    def delete(String id) {
      def instance = Subscription.load(id)
      //Usage.executeUpdate("delete Usage u where u in (select u from Usage as u where u.subscription = :id)", [id: id])
      instance.delete(flush : true)
    }
}
