package com.billingstack.exceptions;

public class BillingStackException extends Exception {

	public BillingStackException() {		
	}

	public BillingStackException(Throwable t) {		
		super(t)
	}

	public BillingStackException(String s, Throwable t) {		
		super(s, t)
	}
	
	public BillingStackException(String s) {
		super(s)
	}
}