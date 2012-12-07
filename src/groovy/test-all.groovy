#!/usr/bin/env groovy
@GrabResolver(name="sonatype", root="http://oss.sonatype.org/content/repositories/snapshots")
@Grab(group="com.ning", module="async-http-client", version="1.8.0-SNAPSHOT")

import com.billingstack.RestClient

random = new Random()

client = new RestClient()

def merchants() {
	println "Merchants ..."
	def merchant = client.post("/merchants",["username":"merchant", "password":"secret0"])
	client.put("/merchants/${merchant.id}",["password":"other"])
	def merchants = client.get("/merchants")
	client.delete("/merchants/${merchant.id}")
}

def products() {
	println "Products ..."
	def merchant = client.post("/merchants",["username":"merchant", "password":"secret0"])
	def product = client.post("/${merchant.id}/products",["name":"product"])
	client.put("/${merchant.id}/products/${product.id}",["name":"other"])
	def products = client.get("/${merchant.id}/products")
	client.delete("/${merchant.id}/products/${product.id}")
	client.delete("/merchants/${merchant.id}")
}

def plans() {
	println "Plans ..."
	def merchant = client.post("/merchants",["username":"merchant", "password":"secret0"])
	def plan = client.post("/${merchant.id}/plans",[name:"plan"])
	client.put("/${merchant.id}/plans/${plan.id}",[:])
	def plans = client.get("/${merchant.id}/plans")
	client.delete("/${merchant.id}/plans/${plan.id}")
	client.delete("/merchants/${merchant.id}")
}

def customers() {
	println "Customers ..."
	def merchant = client.post("/merchants",["username":"merchant4", "password":"secret0"])
	def paymentGateway = client.post("/${merchant.id}/payment-gateways", [
		name : "braintree",
		title : "Braintree",
		description : "Braintree Payments",
	    is_default : true,
	    metadata : [
	    	environment : "sandbox",
	    	merchant_id : "5mtj4sf8bm94v5t8",
	    	public_key : "c6mv34m9yvcv3jpb",
	    	private_key : "3f1dad64a338342ab7172d48bd8ebca4"
	    ]
	])
	def customer = client.post("/${merchant.id}/customers",["username":"customer2", "password":"secret0"])
	client.put("/${merchant.id}/customers/${customer.id}",["password":"other"])
	def plans = client.get("/${merchant.id}/customers")
	client.delete("/${merchant.id}/customers/${customer.id}")
	client.delete("/merchants/${merchant.id}")
}

def subscriptions() {
	println "Subscriptions ..."
	def merchant = client.post("/merchants",["username":"merchant", "password":"secret0"])
	def plan = client.post("/${merchant.id}/plans",[:])
	def customer = client.post("/${merchant.id}/customers",["username":"customer", "password":"secret0"])
	def subscription = client.post("/${merchant.id}/customers/${customer.id}/subscriptions",[
		"plan": [
			"id" : plan.id
		]
	])
	def subscriptions = client.get("/${merchant.id}/customers/${customer.id}/subscriptions")
	client.delete("/${merchant.id}/customers/${customer.id}/subscriptions/${subscription.id}")
	client.delete("/${merchant.id}/customers/${customer.id}")
	client.delete("/${merchant.id}/plans/${plans.id}")
	client.delete("/merchants/${merchant.id}")
}

customers()