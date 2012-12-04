package com.billingstack

import grails.converters.JSON

class ProductsApiController {

    def productsService

    def list() {
		render productsService.findAllWhere(params).collect { it.serialize() } as JSON
    }

    def create(String merchant) {
        try {
            render productsService.create(merchant, request.JSON).serialize() as JSON
        } catch (e) {
            def error = ["error":e.message]
            render error as JSON
            return
        }
    }

    def show(String merchant, String id) {
        render productsService.show(id) as JSON
    }

    def update(String merchant, String id) { 
        render productsService.update(id, request.JSON) as JSON       
    }

    def delete(String id) {
        productsService.delete(id)
        response.status = 204
        render ""
    }

}
