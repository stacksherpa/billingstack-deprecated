package com.billingstack

import grails.converters.JSON

class SubscriptionsApiController {

    def subscriptionsService

    def list() {
        try {
            render subscriptionsService.findAllWhere(params).collect { it.serialize() } as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def create(String merchant, String customer) {
        try {
            def instance = subscriptionsService.create(merchant, customer, request.JSON)
            render instance.serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
        
    }

    def show(String merchant, String customer, String id) {
    try {
            render subscriptionsService.show(id).serialize() as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    }

    def update(String merchant, String customer, String id) { 
        try {
            render subscriptionsService.show(id) as JSON
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }
    	
    }

    def delete(String merchant, String customer, String id) {
        try {
            subscriptionsService.delete(id)
            response.status = 204
            render ""
        } catch(e) {
            response.status = 500
            def error = ["error":e.message]
            render error as JSON
            return
        }

    }

}
