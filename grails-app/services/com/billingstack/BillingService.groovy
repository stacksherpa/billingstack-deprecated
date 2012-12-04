package com.billingstack

import grails.converters.JSON

class BillingService {

	def subscriptionsService
	def invoicesService
	def transactionsService

	def providersService

    def bill(String merchant, String customer, String subscription) {
			if(subscription) {
				billSubscription(Subscription.get(subscription))
			} else if(account) {
				billAccount(Account.get(account))
			} else {
				billMerchant(merchant)
			}
    }

	def billSubscription(Subscription subscription) {
		def invoice = [
				subscription : [
					id : subscription.id
				],
				lines : [],
				subtotal : 0
			]
		def resources = JSON.parse(subscription.resources)
		resources.each { sResource ->
			def provider = providersService.create(sResource.provider.id)
			def usage = provider.usage(sResource)
			println usage
			billUsage(subscription, invoice, usage)
		}
	}
	
	def billAccount(Customer customer) {

		account.subscriptions.each { subscription ->
    		billSubscription(subscription)
    	}
	}
	
	def billMerchant(String merchant) {
		//actually must filter by current day as well
		subscriptionsService.list(merchant : merchant).each { subscription ->
    		billSubscription(subscription)
    	}
	}

    def billUsage(subscription, invoice, usages) {
    	
    	usages.each { usage ->

				def item = subscription.plan.items.find { item ->
	    		item.product.name == usage.product_name
	    	}
	    	if(item) {
	    		if(item.rules) {
	    			item.rules.each { rule ->
			    		if(rule.type == 'fixed') { //fixed
				        invoice.lines << [
				          description : item.product.name,
				          quantity : usage.duration,
				          price : rule.price,
				          subtotal : usage.duration * rule.price / 60
				        ]
				      } else {
				        def range = rule.ranges.find { range ->
				          range.valueFrom <= usage.sum && usage.sum <= range.valueTo
				        }
				        invoice.lines << [
				          description : item.product.name,
				          quantity : usage.sum,
				          price : range.price,
				          subtotal : usage.sum * range.price
				        ]

				      }
			    	}
	    		} else if(item.product.price) {
	    			invoice.lines << [
			          description : item.product.name,
			          quantity : usage.duration,
			          price : item.product.price,
			          subtotal : usage.duration * item.product.price / 60
			      ]
	    		}
	    	}
	    	
    	}
		def createdInvoice = invoicesService.create(subscription.merchant.id, subscription.account.id, invoice)
		invoice
    }
}