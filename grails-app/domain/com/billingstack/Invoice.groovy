package com.billingstack

class Invoice extends BillingEntity {

	Merchant merchant

	Customer customer

	String number
	Date due
	String status
	String currency = "EUR"

	List<InvoiceLine> lines

	BigDecimal subtotal = 0
	BigDecimal taxPercentage
	BigDecimal taxTotal
	BigDecimal total

	static belongsTo = [
		merchant : Merchant,
		customer : Customer
	]

	static hasMany = [
		lines : InvoiceLine
	]

    static constraints = {
    	customer()
    	number(nullable : true)
    	due(nullable : true)
    	status(nullable : true)
    	currency()
    }

    def serialize() {
    	[
			'id' : id,
			'merchant' : [
				id : merchant.id
			],
			'customer' : [
				id : customer.id
			],
			'issued' : dateCreated,
			'due' : dateCreated,
			'currency' : currency,
			'status' : status,
			'subtotal' : subtotal,
			'tax_percentage' : taxPercentage,
			'tax_total' : taxTotal,
			'total' : total,
			'lines' : lines.collect { line ->
				[
					'id' : line.id,
					'description' : line.description,
					'quantity' : line.quantity,
					'price' : line.price,
					'subtotal' : line.subtotal
				]
			}
		]
    }
}
