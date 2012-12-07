package com.billingstack

import grails.converters.JSON

class MerchantsApiController {

  def merchantsService

  def list() {
    try {
        render merchantsService.list(params).collect { it.serialize() } as JSON
	} catch(e) {
		log.error(e.message,e)
		response.status = 500
		def error = ["error":e.message]
		render error as JSON
		return
	} 
  }

  def create() {
    try {
        render merchantsService.create(request.JSON).serialize(true) as JSON
    } catch(e) {
		log.error(e.message,e)
		response.status = 500
		def error = ["error":e.message]
		render error as JSON
		return
    }
  }

  def show(String id) {
    try {
        render merchantsService.get(id).serialize() as JSON
	} catch(e) {
		log.error(e.message,e)
		response.status = 500
		def error = ["error":e.message]
		render error as JSON
		return
	}
  }

  def update(String id) {
    try {
        render merchantsService.update(id, request.JSON).serialize() as JSON
	} catch(e) {
		log.error(e.message,e)
		response.status = 500
		def error = ["error":e.message]
		render error as JSON
		return
	}
  }

  def delete(String id) {
    try {
      merchantsService.delete(id)
      response.status = 204
      render ""
	} catch(e) {
		log.error(e.message,e)
		response.status = 500
		def error = ["error":e.message]
		render error as JSON
		return
	}
  }

}
