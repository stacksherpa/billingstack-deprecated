package com.billingstack

import grails.converters.JSON

class BillingStackApiController {

	def hazelcastService

	def billingService

	def authenticate() {
		try {
			def json = request.JSON
			def user
			if(json.token) {
				//get merchant.id
			} else if(json.api_key && json.api_secret) {
				user = User.findByApiKeyAndApiSecret(json.api_key, json.api_secret)
			} else if(json.merchant) {
				if(json.customer) {
					//customer login
					user = User.where {
						( (merchant.id == json.merchant ||  merchant.name == json.merchant) && (customer.id == json.customer ||  customer.name == json.customer) && username == json.username && password == json.password)
					}.find()
				} else {
					//merchant login
					user = User.where {
						( (merchant.id == json.merchant ||  merchant.name == json.merchant) && customer == null && username == json.username && password == json.password)
					}.find()
				}
			} else {
				//super login
				user = User.where {
					( merchant == null && customer == null && username == json.username && password == json.password )
				}.find()
			}
			if(user) {
				def ur = UserRole.findAllByUser(user)
				def access = [
					token : [
						id : (UUID.randomUUID() as String).replaceAll('-',"")
					]
				]
				if(json.merchant) {
					def merchant = Merchant.findByIdOrName(json.merchant, json.merchant)
					access.merchant = [
						id : merchant.id,
						name : merchant.name,
						endpoint : createLink(controller : 'merchantsApi', params : [id : merchant.id], absolute : true) as String
					]
					if(json.customer) {
						def customer = Customer.findByIdOrName(json.customer, json.customer)
						access.customer = [
							id : customer.id,
							name : customer.name,
							endpoint : createLink(controller : 'customersApi', params : [merchant : merchant.id, id : customer.id], absolute : true) as String
						]
					}
				} else {
					access.endpoint = createLink(controller : 'application', absolute : true) as String
				}
				def tokens = hazelcastService.map("tokens")
				tokens.put(access.token.id, access.token)
				render(text: access as JSON, contentType: 'application/json', encoding:"UTF-8")
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
			render(status : 204, text : "")
    } catch(e) {
		log.error(e.message, e)
        response.status = 500
        def error = ["error":e.message]
        render(text: error as JSON, contentType: 'application/json', encoding:"UTF-8")
        return
    }
	}

}