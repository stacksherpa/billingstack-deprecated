package com.billingstack

import grails.converters.JSON

class PaymentGatewaysService {

	def available = [
		[name : "braintree", class : com.billingstack.braintree.Braintree]
	]

    def load(String merchant) {
		def configuration = PaymentGateway.findWhere('merchant.id' : merchant)
		if(!configuration) {
			throw new RuntimeException("Extension not configured yet.")
		}
		def extension = available.find { it.name == configuration.name }
		extension["class"].create(JSON.parse(configuration.metadata))
  	}

}
