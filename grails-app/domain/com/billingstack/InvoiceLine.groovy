package com.billingstack

class InvoiceLine extends BillingEntity {

  Merchant merchant

  Customer customer

	Invoice invoice

	String description
	BigDecimal quantity
	BigDecimal price
	BigDecimal subtotal

  static constraints = {
    description(nullable : true)
    quantity(nullable : true)
    price(nullable : true)
    subtotal(nullable : true)
  }

}
