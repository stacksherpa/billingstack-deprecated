package com.billingstack

import grails.converters.JSON

class BillingStackApiController {

	def hazelcastService

	def billingService

	def authenticate() {
		try {
			def json = request.JSON
			def merchant
			if(json.api_key && json.api_secret) {
				merchant = User.findByApiKeyAndApiSecret(json.api_key, json.api_secret)
			} else if (json.username && json.password) {
				merchant = User.findByUsernameAndPassword(json.username, json.password)
			}
			if(merchant) {
				def token = [id : (UUID.randomUUID() as String).replaceAll('-',""), endpoint : createLink(params : [merchant : merchant.id], absolute : true) as String]
				def tokens = hazelcastService.map("tokens")
				tokens.put(token.id, token)
				render(text: token as JSON, contentType: 'application/json', encoding:"UTF-8")
			} else {
				response.status = 403
				def error = ["error":"Merchant not found"]
				render(text: error as JSON, contentType: 'application/json', encoding:"UTF-8")
			}
    } catch(e) {
        response.status = 500
        def error = ["error":e.message]
        render(text: error as JSON, contentType: 'application/json', encoding:"UTF-8")
        return
    }

	}

	def logout() {
		try {
			def tokens = hazelcastService.map("tokens")
    } catch(e) {
        response.status = 500
        def error = ["error":e.message]
        render(text: error as JSON, contentType: 'application/json', encoding:"UTF-8")
        return
    }	
	}

	def info() {
		try {
			def info = [:]
			render info as JSON
    } catch(e) {
        response.status = 500
        def error = ["error":e.message]
        render(text: error as JSON, contentType: 'application/json', encoding:"UTF-8")
        return
    }
	}
	
	def bill(String merchant, String account, String subscription) {
		try {
			billingService.bill(merchant, account, subscription)
			response.status = 204
			render ""
    } catch(e) {
        response.status = 500
        def error = ["error":e.message]
        render(text: error as JSON, contentType: 'application/json', encoding:"UTF-8")
        return
    }
	}

}