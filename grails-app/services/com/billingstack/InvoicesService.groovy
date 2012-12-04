package com.billingstack

class InvoicesService {

    def findAllWhere(filters) {
      def query = [:]
      def invoices
        if(filters.merchant) {
            query['merchant.id'] = filters.merchant   
        }
      if(filters.customer) {
            query['customer.id'] = filters.customer 
      }
      Invoice.findAllWhere(query)
    }

    def create(String merchant, String customer, json) {
      def invoice = new Invoice()
      invoice.merchant = Merchant.get(merchant)
      invoice.customer = Customer.load(customer)
      json.lines.each { line ->
        invoice.addToLines(line)
        invoice.subtotal += line.subtotal
      }
      invoice.currency = invoice.merchant.currency
      invoice.status = "created"
      invoice.taxPercentage = 0.21 //json.tax_percentage
      invoice.taxTotal = invoice.taxPercentage * invoice.subtotal
      invoice.total = invoice.subtotal + invoice.taxTotal
      invoice.save(flush: true, failOnError : true)
    }

    def show(String id) { 
    	Invoice.get(id)
    }

    def update(String id) { 
    	Invoice.get(id)
    }

    def delete(String id) { 
    	def instance = Invoice.get(id)
      instance.delete(flush : true)
    }

}
