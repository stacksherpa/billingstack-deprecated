package com.billingstack

import com.ning.http.client.*

class NotificationsService {
	
		def grailsApplication
	
		def slurper = new groovy.json.JsonSlurper()
		def builder = new groovy.json.JsonBuilder()
	
		def http = new AsyncHttpClient()

    def push(endpoint, type, payload) {
			try {
				def response = http.preparePost(endpoint)
						.setBody(builder.call(payload).toString())
						.execute().get()
			} catch(e) {
				log.error e.message
			}
    }
}
