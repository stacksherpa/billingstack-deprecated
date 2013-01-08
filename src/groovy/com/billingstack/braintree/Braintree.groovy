package com.billingstack.braintree

import com.braintreegateway.*

class Braintree {

  static final NAME = "braintree"
  static final TITLE = "Braintree"
  static final DESCRIPTION = "Braintree"

  static match(String name) {
    return NAME.equals(name)
  }

	static create(configuration) {
		new Braintree(
			gateway : new com.braintreegateway.BraintreeGateway(
				"production".equals(configuration.environment) ? Environment.PRODUCTION : Environment.SANDBOX,
				configuration.merchant_id,
				configuration.public_key,
				configuration.private_key
			)
		)
	}

  def gateway

  def createAccount(data) {
  	CustomerRequest request = new CustomerRequest()
    	.id(data.account.id as String)
		gateway.customer().create(request);
  }

  def createCreditCard(account, data) {
		println data
  	CreditCardRequest request = new CreditCardRequest()
	    .customerId(account as String)
	    .number(data.number)
	    .expirationDate(data.expiration)
	    .cardholderName(data.holder);

		gateway.creditCard().create(request);
  }

  def listCreditCards(data) {
  	def customer = gateway.customer().find(data.customer.id);
		customer.creditCards.collect {
			[
				id : it.token,
				type : it.cardType,
				number : it.maskedNumber,
				expiration : it.expirationDate
			]
		}
  }

  def listTransactions(data) {
  	def request = new TransactionSearchRequest()
  	gateway.transaction().search(request).collect {
  		println it.properties
  		[
  			id : it.id,
  			invoice : [
				id : it.orderId
			],
  			status : it.status.toString(),
  			amount : it.amount
  		]
  	}
  }

  def createTransaction(account, data) {
  	TransactionRequest request = new TransactionRequest()
  		.orderId(data.invoice.id as String)
	    .amount(data.amount)
	    .customerId(account as String)
	    .options()
        .submitForSettlement(true)
        .done();
	    //.paymentMethodToken("the_payment_method_token");

		gateway.transaction().sale(request);
  }

  def showTransaction(id) {
  	gateway.transaction().find(id)
  }

  def submitForSettlement(id) {
  	gateway.transaction().submitForSettlement(id)
  }

  def voidTransaction(id) {
  	gateway.transaction().voidTransaction(id);
  }

  def refundTransaction(id) {
  	gateway.transaction().refund(id);
  }

}
