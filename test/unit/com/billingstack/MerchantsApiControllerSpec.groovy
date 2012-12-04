package com.billingstack

import grails.test.mixin.*
import spock.lang.Specification

@TestFor(MerchantsApiController)
class MerchantsApiControllerSpec extends Specification {

	def "test list merchants"() {
		when:
		controller.list()
		then:
		response.status == 200
	}
	
	def "test create merchant"() {
		given: "build the merchant json"
		controller.request.content = """
		{
			"username" : "luis@woorea.es"
		}
		"""
		when:
		controller.create()
		then:
		response.status == 200
	}
	
	/*
	def 'test update merchant'() {
		when:
		controller.update()
		then:
		response.status == 200
	}
	
	def 'test delete merchant'() {
		when:
		controller.delete()
		then:
		response.status == 204
	}
	*/

}