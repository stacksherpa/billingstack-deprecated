import com.billingstack.*

class BootStrap {

    def init = { servletContext ->
    	Role.newInstance(name : "MERCHANT_ADMIN").save()
    	Role.newInstance(name : "CUSTOMER_ADMIN").save()
    }
    def destroy = {
    }
}
