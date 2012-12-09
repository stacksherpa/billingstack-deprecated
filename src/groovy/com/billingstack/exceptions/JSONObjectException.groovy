package com.billingstack.exceptions;

public class JSONObjectException extends BillingStackException {

	public JSONObjectException() {
		super()
	}

	public JSONObjectException(String s, Throwable t) {
		super(s, t)
	}

	public JSONObjectException(Throwable t) {
		super(t)
	}
	
	public JSONObjectException(String s) {
		super(s)
	}	
}