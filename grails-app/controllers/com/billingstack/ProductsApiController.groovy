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
        try {
          def product = productsService.show(id)
          if(product) {
            render product.serialize() as JSON
          } else {
            response.status = 404
            render ""
          }
        } catch(e) {
          def error = ["error":e.message]
          render error as JSON
          return
        }
    }

    def update(String merchant, String id) { 
        render productsService.update(id, request.JSON).serialize() as JSON       
    }

    def delete(String id) {
        productsService.delete(id)
        response.status = 204
        render ""
    }

}
