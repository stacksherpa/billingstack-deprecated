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
		def product = Product.get(id)
  	product.properties = json
  	product
  }

  def delete(String id) {
      Product.get(id).delete(flush:true)
  }

}
