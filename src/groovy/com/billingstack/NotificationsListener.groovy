package com.billingstack

import com.ning.http.client.*

import org.springframework.context.ApplicationEvent
import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEventListener;
import org.grails.datastore.mapping.engine.event.PostInsertEvent
import org.grails.datastore.mapping.engine.event.PostUpdateEvent
import org.grails.datastore.mapping.engine.event.PostDeleteEvent

class NotificationsListener extends AbstractPersistenceEventListener {
  
	def grailsApplication

	def slurper = new groovy.json.JsonSlurper()
	def builder = new groovy.json.JsonBuilder()

	def http = new AsyncHttpClient()

  public NotificationsListener(final grailsApplication, final Datastore datastore) {
    super(datastore)
		this.grailsApplication = grailsApplication
  }

  def push(endpoint, payload) {
		try {
			def response = http.preparePost(endpoint)
					.setBody(builder.call(payload).toString())
					.execute().get()
		} catch(e) {
			log.error e.message
		}
  }
  
  @Override
  protected void onPersistenceEvent(final AbstractPersistenceEvent event) {
			push(
				grailsApplication.config.billingstack.push_notifications_endpoint,
				[type : event.eventType, entity : event.entityObject]
			)
  }

  public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
    return PostInsertEvent.class.isAssignableFrom(eventType) ||
           PostUpdateEvent.class.isAssignableFrom(eventType) ||
           PostDeleteEvent.class.isAssignableFrom(eventType)
  }
}