package com.billingstack

class UserRole implements Serializable {
  
    Merchant merchant
    
    Customer customer

    User user

    Role role
    
    static constraints = {
      merchant nullable : true, unique : ['customer','user','role']
      customer nullable : true
    }

    static mapping = {
      id composite: ['user', 'role']
      version false
    }
}
