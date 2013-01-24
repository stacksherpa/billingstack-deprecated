package com.billingstack

class Merchant extends BillingEntity {

  String name

  String language = "EN"
  String currency = "USD"

  static constraints = {
		
  }

  def serialize() {
    def json = [
      'id' : id,
      'name' : name,
      'language' : language,
      'currency' : currency
    ]
    json
  }
}
