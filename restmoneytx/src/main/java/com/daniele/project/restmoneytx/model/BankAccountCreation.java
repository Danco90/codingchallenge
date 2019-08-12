package com.daniele.project.restmoneytx.model;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_EMPTY)
public class BankAccountCreation {
	
	private long id;
	private String name;
	private long number;
	private double balance;
	
	public BankAccountCreation() {
		
	}
	
	public BankAccountCreation(String name, long number, double balance ) {
		this.name = name;
		this.number = number;
		this.balance = balance;
	}
	
	public BankAccountCreation(long id, String name, long number, double balance ) {
		this(name, number, balance);
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
