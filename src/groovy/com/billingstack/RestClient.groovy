package com.billingstack

import com.ning.http.client.*

class RestClient {
	
	def endpoint = "http://localhost:8080/billingstack"

	def slurper = new groovy.json.JsonSlurper()
	def builder = new groovy.json.JsonBuilder()

	def http = new AsyncHttpClient()
	
	public RestClient() {
	
	}
	
	public RestClient(String endpoint) {
		this.endpoint = endpoint
	}

	def get(path) {
		def response = http.prepareGet(endpoint + path)
				.execute().get()
		if(response.responseBody) {
			println response.responseBody
			slurper.parseText(response.responseBody)
		} else {
			[:]
		}
	}
	def delete(path) {
		def response = http.prepareDelete(endpoint + path)
				.execute().get()
		if(response.responseBody) {
			println response.responseBody
			slurper.parseText(response.responseBody)
		} else {
			[:]
		}
	}
	def post(path, data) {
		builder.call(data)
		println builder.toString()
		def response = http.preparePost(endpoint + path)
				.setBody(builder.toString())
				.execute().get()
		if(response.responseBody) {
			println response.responseBody
			slurper.parseText(response.responseBody)
		} else {
			[:]
		}
	}
	def put(path, data) {
		def response
		if(data) {
			builder.call(data)
			println builder.toString()
			response = http.preparePut(endpoint + path)
					.setBody(builder.toString())
					.execute().get()
		} else {
			response = http.preparePut(endpoint + path).execute().get()
		}
		if(response.responseBody) {
			println response.responseBody
			slurper.parseText(response.responseBody)
		} else {
			[:]
		}
	}
}