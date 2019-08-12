package com.daniele.project.restmoneytx.model;

/**
 * 
 * @author matteodaniele
 * @update 12/08/2019
 * @version 1.0.1
 */

public class AccountConversionBean {
	
	private final String DEFAULT_EURO_CURRENCY="EUR";
	
	private static final String CURRENCY = "[A-Z_-]+";
    private static final String INVALID_CURRENCY_MESSAGE =
            "Invalid currency parameter, not an uppercase string";

	private Long id;
	private String ownerName;
	private long acctNumber;
	private String currency = DEFAULT_EURO_CURRENCY;
	private double balance;
	private int port;
	
	public AccountConversionBean() {
	}

	public AccountConversionBean(Long id, String ownerName, long acctNumber, String currency, double balance) {
		this(id, ownerName, acctNumber, balance);
		this.currency = currency;
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
	
	public AccountConversionBean(String ownerName, long acctNumber, String currency, double balance) {
		this(ownerName, acctNumber, balance);
		this.currency = currency;
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
	
	public int getPort() {
	    return port;
	}
	
	public void setPort(int port) {
	    this.port = port;
	}


}