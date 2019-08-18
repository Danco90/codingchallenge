package com.daniele.project.restmoneytx.model;

/**
 * 
 * @author matteodaniele
 * @update 12/08/2019
 * @version 1.0.1
 */

public class AccountConversionBean {

	private long id;
	private String ownerName;
	private long acctNumber;
	private double balance;
	
	public AccountConversionBean() {
	}
	
	public AccountConversionBean(long id, String ownerName, long acctNumber, double balance) {
		this(ownerName, acctNumber, balance);
		this.id = id;
	}
	
	public AccountConversionBean(String ownerName, long acctNumber, double balance) {
		this.ownerName = ownerName;
		this.acctNumber = acctNumber;
		this.balance = balance;
	}

	
	public long getId() {
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
	
	@Override
	public String toString() {
		return "AccountConversionBean [id=" + id + ", ownerName=" + ownerName + ", acctNumber=" + acctNumber
				+ ", balance=" + balance + "]";
	}
	

	public void setId(long id) {
		this.id = id;
	}

	@Override 
	public boolean equals(Object obj) {
		if (obj == this) { return true; } 
		if (obj == null || obj.getClass() != this.getClass()) 
		{ return false; } 
		AccountConversionBean guest = (AccountConversionBean) obj; 
		return id == guest.getId() && 
		   (ownerName != null && ownerName.equals(guest.getOwnerName())) && 
		   acctNumber == guest.getAcctNumber() && 
		   balance == guest.getBalance();  
	}

}