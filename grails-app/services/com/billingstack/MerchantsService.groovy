package com.billingstack

class MerchantsService {
  
  def grailsApplication
  
  def notificationsService

  def usersService

  def productsService

  def list(filters) { 
    Merchant.list(filters)
  }

  def create(json) {
    def merchant = Merchant.newInstance(
      name : json.name,
      currency : json.currency ?: "USD",
      language : json.language ?: "EN"
    )
    merchant.save(flush : true, failOnError: true)
    def user = usersService.create(merchant.id, null, json.user)
    def userRole = UserRole.newInstance(
      merchant : merchant,
      user : user,
      role : Role.findByName("MERCHANT_ADMIN")
    ).save(failOnError: true)
    //this should be part of a plugin in a near future
    if(grailsApplication.config.billingstack.load_ceilometer_products) {
      [
        [name : "instance", type : "gauge", measure : "unit", resource : "instance_id", description : "Duration of instance"],
        [name : "memory", type : "gauge", measure : "mb", resource : "instance_id", description : "Volume of RAM in MB"],
        [name : "vcpus", type : "gauge", measure : "vcpu", resource : "instance_id", description : "Number of VCPUs"],
        [name : "root_disk_size", type : "gauge", measure : "gb", resource : "instance_id", description : "Size of root disk in GB"],
        [name : "ephemeral_disk_size", type : "gauge", measure : "gb", resource : "instance_id", description : "Size of ephemeral disk in GB"],
        [name : "disk.read.requests", type : "cumulative", measure : "unit", resource : "instance_id", description : "Number of disk read requests"],
        [name : "disk.read.bytes", type : "cumulative", measure : "bytes", resource : "instance_id", description : "Volume of disk read in bytes"],
        [name : "disk.write.requests", type : "cumulative", measure : "unit", resource : "instance_id", description : "Number of disk write requests"],
        [name : "disk.write.bytes", type : "cumulative", measure : "bytes", resource : "instance_id", description : "Volume of disk write in bytes"],
        [name : "cpu", type : "cumulative", measure : "unit", resource : "seconds", description : "CPU time used"],
        [name : "network.incoming.bytes", type : "cumulative", measure : "bytes", resource : "instance_id", description : "number of incoming bytes on the network"],
        [name : "network.outgoing.bytes", type : "cumulative", measure : "bytes", resource : "instance_id", description : "number of outgoing bytes on the network"],
        [name : "network.incoming.packets", type : "cumulative", measure : "packets", resource : "instance_id", description : "number of incoming packets"],
        [name : "network.outgoing.packets", type : "cumulative", measure : "packets", resource : "instance_id", description : "number of outgoing packets"],
        [name : "image", type : "gauge", measure : "unit", resource : "image_id", description : "Image polling -> it (still) exists"],
        [name : "image_size", type : "gauge", measure : "bytes", resource : "image_id", description : "Uploaded image size"],
        [name : "image_download", type : "gauge", measure : "bytes", resource : "image_id", description : "Image is downloaded"],
        [name : "image_serve", type : "gauge", measure : "bytes", resource : "image_id", description : "Image is served out"],
        [name : "volume", type : "gauge", measure : "unit", resource : "measure_id", description : "Duration of volume"],
        [name : "volume_size", type : "gauge", measure : "gb", resource : "measure_id", description : "Size of measure"]
      ].each {
        productsService.create(merchant.id, it)
      }
    }
    def result = [
      merchant : merchant.serialize(),
      user : userRole.user.serialize()
    ]
    result.user.roles = ["MERCHANT_ADMIN"]
    notificationsService.push(grailsApplication.config.billingstack.push_notifications_endpoint,'merchant.created',result)
    result
  }

  def show(String id) {
      Merchant.get(id)
  }

  def update(String id, json) { 
    def merchant = Merchant.get(id)
    merchant.properties = json
    merchant
  }

  def delete(String id) {
      UserRole.executeUpdate "DELETE FROM UserRole WHERE merchant.id = :id", [id: id]
      User.executeUpdate "DELETE FROM User WHERE merchant.id = :id", [id: id]
      InvoiceLine.executeUpdate "DELETE FROM InvoiceLine WHERE merchant.id = :id", [id: id]
      Invoice.executeUpdate "DELETE FROM Invoice WHERE merchant.id = :id", [id: id]
      Usage.executeUpdate "DELETE FROM Usage WHERE merchant.id = :id", [id: id]
      Subscription.executeUpdate "DELETE FROM Subscription WHERE merchant.id = :id", [id: id]
      Customer.executeUpdate "DELETE FROM Customer WHERE merchant.id = :id", [id: id]
      PlanProductRuleRange.executeUpdate "DELETE FROM PlanProductRuleRange WHERE merchant.id = :id", [id: id]
      PlanProductRule.executeUpdate "DELETE FROM PlanProductRule WHERE merchant.id = :id", [id: id]
      PlanProduct.executeUpdate "DELETE FROM PlanProduct WHERE merchant.id = :id", [id: id]
      Plan.executeUpdate "DELETE FROM Plan WHERE merchant.id = :id", [id: id]
      Product.executeUpdate "DELETE FROM Product WHERE merchant.id = :id", [id: id]
      PaymentGateway.executeUpdate "DELETE FROM PaymentGateway WHERE merchant.id = :id", [id: id]
      Merchant.load(id).delete(flush : true)
  }
  
}
  