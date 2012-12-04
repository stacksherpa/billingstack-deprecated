package com.billingstack

class AuthorizationFilters {

    def hazelcastService

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                def tokens = hazelcastService.map("tokens")
                println "Hazelcast : " + tokens
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
