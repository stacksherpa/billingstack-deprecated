package com.billingstack

import org.codehaus.groovy.runtime.TimeCategory
import java.text.SimpleDateFormat

import grails.converters.JSON

class UsageApiController {

    def sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    
    def list() {
    	def usage
    	if(params.subscription) {
    		usage = Usage.findAllWhere('subscription.id' : params.subscription)
  		} else {
  			usage = Usage.list()
  		}
    	render usage.collect { it.serialize() } as JSON
    }

    def create(String merchant, String customer, String subscription) {
        sdf.timeZone = TimeZone.getTimeZone("UTC") 

        def json = request.JSON
        try {
            def instance = new Usage(
                subscription : Subscription.load(subscription),
                product : Product.load(json.product.id),
                duration : json.duration,
                sum : json.sum,
                max : json.max,
                startTimestamp : sdf.parse(json.start_timestamp),
                endTimestamp : sdf.parse(json.end_timestamp)
            )
            instance.save(flush: true, failOnError : true)
            render instance.serialize() as JSON
        } catch(e) {
            def error = ["error": json.product.id + " not found"]
            render error as JSON
            return
        }

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
