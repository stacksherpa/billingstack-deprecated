package com.billingstack

import grails.converters.JSON

class RolesApiController {

  def list() {
    try {
        render Role.list().collect { it.serialize() } as JSON
		} catch(e) {
			log.error(e.message,e)
			response.status = 500
			def error = ["error":e.message]
			render error as JSON
			return
		} 
  }

}
