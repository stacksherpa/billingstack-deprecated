package com.billingstack

import grails.test.mixin.*
import spock.lang.Specification

@TestFor(MerchantsApiController)
class MerchantsApiControllerSpec extends Specification {
	
	def merchantsService = Mock(MerchantsService)
	
	def setup() {
		controller.merchantsService = merchantsService
		mockLogging(MerchantsService, true) 
	}

	def "test list merchants"() {
		when:
		controller.list()
		then:
		response.status == 200
	}
	
	/*
	def "test create merchant"() {
		given: "build the merchant json"
		request.content = "{}".bytes
		when:
		controller.create()
		then:
		1 * merchantsService.create(_) >> [:]
		0 * _._
		response.status == 200
	}
	
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