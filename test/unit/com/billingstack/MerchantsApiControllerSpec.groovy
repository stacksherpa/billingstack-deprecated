package com.billingstack

import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import spock.lang.*

@TestFor(MerchantsApiController)
class MerchantsApiControllerSpec extends UnitSpec {

	def merchantsService = Mock(MerchantsService)

	def setup() {
		controller.merchantsService = merchantsService
		mockLogging(MerchantsApiController, true)
		mockDomain(Merchant)
	}

	def "get merchants list"() {
		when:
			controller.list()
		then:
			response.status == 200
	}

	def "create a merchant"() {
		setup: 
			controller.request.contentType = 'application/json'
		 	controller.request.content = objToCreate
		when:
			controller.create()
		then:
			1 * merchantsService.create(_) >> new Merchant()
			0 * _._
			response.status == 200
		where:
			objToCreate = '{ "password": "secret0", "currency": "EUR" }'
	}
	
	def "create a merchant without name"() {
		setup:
			 controller.request.contentType = 'application/json'
			 controller.request.content = objToCreate
		when:
			controller.create()
		then:
			1 * merchantsService.create(_) >> { throw JSONObjectException("Username is mandatory") }
			0 * _._
			response.status == 500
		where:
			objToCreate = '{ "password": "secret0", "currency": "EUR" }'
	}
	
	def "update merchant"() {
		setup:
			 controller.request.contentType = 'application/json'
			 controller.params.id = idObjToModify
			 controller.request.content = newValues
		when:
			controller.update()
		then:
			1 * merchantsService.update(_) >> new Merchant()
			0 * _._
			response.status == 200
		where:
			idObjToModify = '1'
			newValues = '{ "currency": "EUR" }'
	}
	
	def "delete merchant"() {
		setup:
			 controller.request.contentType = 'application/json'
			 controller.params.id = idObjToDelete
		when:
			controller.delete()
		then:
			1 * merchantsService.delete(_) >> void
			0 * _._
			response.status == 204
		where:
			idObjToDelete = '1'
	}
}
