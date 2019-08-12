package com.daniele.project.restmoneytx.exception;

public class AccountMovementException extends Exception {
	
	private static final long serialVersionUID = -3332292346834265371L;
	
	public AccountMovementException(String mex, String id) {
		super("AccountMovementException with acct.num="+id+"."+mex);
	}

}
