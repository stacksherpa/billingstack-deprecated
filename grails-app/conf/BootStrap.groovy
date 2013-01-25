import com.billingstack.*

import org.codehaus.groovy.grails.commons.ApplicationAttributes

class BootStrap {
  
    def grailsApplication

    def init = { servletContext ->
			def applicationContext = servletContext.getAttribute(ApplicationAttributes.APPLICATION_CONTEXT)
	
			grailsApplication.mainContext.eventTriggeringInterceptor.datastores.each { k, datastore ->
				applicationContext.addApplicationListener new NotificationsListener(grailsApplication, datastore)
			}
	
      Role.newInstance(name : "BILLINGSTACK_ADMIN").save()
      Role.newInstance(name : "MERCHANT_ADMIN").save()
      Role.newInstance(name : "CUSTOMER_ADMIN").save()
    }
    def destroy = {
    }
}
