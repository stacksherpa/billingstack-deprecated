package com.billingstack

class InvoiceLine extends BillingEntity {

	Invoice invoice

	String description
  	BigDecimal quantity
  	BigDecimal price
  	BigDecimal subtotal

  	static belongsTo = [
  		invoice : Invoice
  	]

    static constraints = {
      description(nullable : true)
      quantity(nullable : true)
      price(nullable : true)
      subtotal(nullable : true)
    }
}
