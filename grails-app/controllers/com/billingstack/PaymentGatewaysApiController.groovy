package com.billingstack

import grails.converters.JSON

class PaymentGatewaysApiController {

  def paymentGatewaysService

  def list() {
    def filters = [
    	"merchant.id" : params.merchant
	]
	if(params.name) {
		filters.name = params.name
	}
    render PaymentGateway.findAllWhere(filters).collect { it.serialize() } as JSON
  }

  def create(String merchant, String extension) {
      try {
          def json = request.JSON
          def instance = new PaymentGateway(
              merchant : Merchant.load(merchant),
              type : extension,
              name : json.name,
              title : json.title,
              description : json.description,
			  isDefault : json.is_default ?: Boolean.FALSE,
              metadata : json.metadata.toString()
          )
          instance.save(flush: true, failOnError : true)
          render instance.serialize() as JSON
      } catch (e) {
          response.status = 500
          def error = ["error":e.message]
          render error  as JSON
          return
      }  
  }

  def show(String merchant, String id) {
  	render PaymentGateway.get(id).serialize() as JSON
  }

  def update(String merchant, String id) {
		def configuration = PaymentGateway.get(id)
		def json = request.JSON
		configuration.title = json.title
		configuration.description = json.description
		configuration.isDefault = json.is_default
		configuration.metadata = json.metadata.toString()
		configuration.save(flush : true)
		render configuration.serialize() as JSON
  }

  def delete(String merchant, String id) {
    def instance = PaymentGateway.get(id)
    if (!instance) {
        render(status: 404, model : ["error":message(code: 'default.not.found.message', args: [message(code: 'plan.label', default: 'UsageProvider'), id])]) as JSON
        return
    }
    instance.delete(flush : true)
    response.status = 204
    render ""
  }

  def action(String merchant, String extension, String id) {
      def extensionInstance = paymentGatewaysService.create(id)
      def json = request.JSON
      def action = json.entrySet().iterator().next()
      def result = extensionInstance."${action.key}"(createLink(controller : "billingStackApi", params : [merchant : merchant], absolute : true) as String, action.value)
      response.status = 200
      render result as JSON
  }

}
