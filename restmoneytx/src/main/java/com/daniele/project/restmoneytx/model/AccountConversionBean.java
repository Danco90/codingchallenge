package com.daniele.project.restmoneytx.model;

/**
 * 
 * @author matteodaniele
 * @update 12/08/2019
 * @version 1.0.1
 */

public class AccountConversionBean {

	private Long id;
	private String ownerName;
	private long acctNumber;
	private double balance;
	
	public AccountConversionBean() {
	}
	
	public AccountConversionBean(Long id, String ownerName, long acctNumber, double balance) {
		this(ownerName, acctNumber, balance);
		this.id = id;
	}
	
	public AccountConversionBean(String ownerName, long acctNumber, double balance) {
		this.ownerName = ownerName;
		this.acctNumber = acctNumber;
		this.balance = balance;
	}

	
	public Long getId() {
		return id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public long getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(long acctNumber) {
		this.acctNumber = acctNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}