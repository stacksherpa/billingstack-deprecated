package com.billingstack

class PlanProductRule extends BillingEntity {

  Merchant merchant
  Plan plan
  Product product
  String type
  BigDecimal price

  static constraints = {
    merchant()
    plan()
    product()
    type()
    price(nullable : true)
  }
  
  def findAllRuleRanges() {
    PlanProductRuleRange?.findAllByRule(this)
  }
  
   def serialize() {
    def json = [id : id, type: type]
    if(type == 'fixed') {
      json.price = price
    } else if (type == 'volume-range'){
        json.ranges = findAllRuleRanges().collect { range ->
          range.serialize()
        }
    }
    json
   }
}
