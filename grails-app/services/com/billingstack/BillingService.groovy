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
			} else if(customer) {
				billAccount(Account.get(customer))
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
		billUsage(subscription, invoice, subscription.usages)
	}
	
	def billAccount(Customer customer) {

		customer.subscriptions.each { subscription ->
    		billSubscription(subscription)
    	}
	}
	
	def billMerchant(String merchant) {
		//actually must filter by current day as well
		subscriptionsService.findAllWhere(merchant : merchant).each { subscription ->
    		billSubscription(subscription)
    	}
	}

    def billUsage(subscription, invoice, usages) {
    	
    	usages.each { usage ->
			def item = subscription.plan.products.find { item ->
				item.product.name == usage.product.name
			}
			if(item) {
				if(item.rules) {
					item.rules.each { rule ->
						if(rule.type == 'fixed') { //fixed
							usage.price = rule.price
							usage.total = rule.price * usage.value
						} else {
							def range = rule.ranges.find { range ->
								(!range.valueFrom || range.valueFrom <= usage.value) &&
								(!range.valueTo || usage.value <= range.valueTo)
							}
							usage.price = range.price
							usage.total = range.price * usage.value
						}
			    	}
				} else if(item.product.price) {
					usage.price = item.product.price
					usage.total = usage.value * item.product.price
				}
				invoice.lines << [
					description : item.product.name,
					quantity : usage.value,
					price : usage.price,
					subtotal : usage.total
				]
			}
    	}
		def createdInvoice = invoicesService.create(subscription.merchant.id, subscription.customer.id, invoice)
		invoice
    }
}