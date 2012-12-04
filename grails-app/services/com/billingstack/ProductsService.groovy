package com.billingstack

class ProductsService {

  def findAllWhere(filters) {
	def query = [:]
	if(filters.merchant) {
		query['merchant.id'] = filters.merchant
	}
	Product.findAllWhere(query)
  }

  def create(merchant, json) {
    def product = new Product(merchant : Merchant.load(merchant))
    product.properties = json
    product.save(flush: true, failOnError : true)
  }

  def show(String id) {
      Product.get(id)
  }

  def update(id, json) { 
      Product.get(id)        
  }

  def delete(String id) {
      def instance = Product.get(id)
      instance.delete(flush:true)
      render(status : 204)
  }

}
