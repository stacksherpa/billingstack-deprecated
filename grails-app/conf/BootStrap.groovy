import com.billingstack.*

class BootStrap {

		def merchantsService

		def customersService

    def init = { servletContext ->
    	Role.newInstance(name : "MERCHANT_ADMIN").save()
    	Role.newInstance(name : "CUSTOMER_ADMIN").save()
    	def merchant = merchantsService.create([
    		name : "stacksherpa",
    		user : [
    			username : "david",
    			password : "secret0"
    		]
    	])
    	def customer = customersService.create(merchant.id,[
    		name : "woorea",
    		user : [
    			username : "luis",
    			password : "secret0"
    		]
    	])
    	println User.list()
    	println Customer.list()
    }
    def destroy = {
    }
}
