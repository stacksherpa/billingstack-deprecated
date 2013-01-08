package com.billingstack

class TransactionsService {

    def paymentGatewaysService

    def list(filters) {
        def paymentGateway = paymentGatewaysService.load(filters.merchant)
        paymentGateway.listTransactions()
    }

    def create(String merchant, String account, json) {
        def paymentGateway = paymentGatewaysService.load(merchant)
				paymentGateway.createTransaction(account, json).target
    }

    def show() {
      throw new UnsupportedOperationException("Not implemented yet")
    }

    def action(String merchant, String transaction, String action) {
      //criteria
      def merchantRef = Merchant.findByName(merchant)
      def paymentGateway = paymentGatewaysService.load(merchantRef.id)
      switch(action) {
        case "submitForSettlement":
          paymentGateway.submitForSettlement(transaction)
          break
        case "void":
          paymentGateway.voidTransaction(transaction)
          break
        case "refund":
          paymentGateway.refund(transaction)
          break
        default:
          break
      }
    }

}
