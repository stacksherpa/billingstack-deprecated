import com.billingstack.*

class BootStrap {
  
    def grailsApplication

    def init = { servletContext ->
      Role.newInstance(name : "BILLINGSTACK_ADMIN").save()
      Role.newInstance(name : "MERCHANT_ADMIN").save()
      Role.newInstance(name : "CUSTOMER_ADMIN").save()
    }
    def destroy = {
    }
}
