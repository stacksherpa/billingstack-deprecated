package com.billingstack

import groovy.time.TimeCategory
import java.text.SimpleDateFormat

import grails.converters.JSON

class UsageApiController {

    def sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    
    def list(String merchant, String customer, String subscription) {
			def filters = ['merchant.id' : merchant]
			if(customer) {
				filters['customer.id'] = customer
			}
			if(subscription) {
				filters['subscription.id'] = subscription
			}
      render Usage.findAllWhere(filters).collect { it.serialize() } as JSON
    }

    def create(String merchant, String customer, String subscription) {
      sdf.timeZone = TimeZone.getTimeZone("UTC") 
      def usages = []
      request.JSON.each {
        try {
          def usage = new Usage()
          if(subscription) {
            usage.subscription = Subscription.load(subscription)
          } else {
            usage.subscription = Subscription.findWhere(provider : it.provider, resource : it.resource)
          }
          if(usage.subscription) {
            usage.merchant = usage.subscription.merchant
            usage.customer = usage.subscription.customer
            usage.product = Product.findByIdOrName(it.product.id, it.product.name)
            usage.value = it.value
            usage.measure = it.measure
            try {
              usage.startTimestamp = sdf.parse(it.start_timestamp)
                      usage.endTimestamp = sdf.parse(it.end_timestamp)
            } catch (e) {
              usage.startTimestamp = new Date()
                      usage.endTimestamp = new Date()
            }
            usages << usage.save(flush: true, failOnError : true)
          } else {
            log.error("subscription not found for usage : $it")
            def error = ["error": "subscription not found for usage : $it"]
            render error as JSON
            return
          }
        } catch(e) {
          log.error(e.message, e)
          def error = ["error": e.message]
          render error as JSON
          return
        }
      }
      render usages.collect { it.serialize() } as JSON
    }

    def show(String merchant, String customer, String subscription, String id) { 
      render Usage.get(id) as JSON
    }

    def update(String merchant, String customer, String subscription, String id) { 
      render Usage.get(id) as JSON
    }

    def delete(String merchant, String customer, String subscription, String id) {
      def instance = Usage.get(id)
      instance.delete(flush : true)
      render ""
    }

}
