package com.daniele.project.restmoneytx.model;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * 
 * @author mdaniele
 * @update 12/08/2019
 * @version 0.6.1
 */

public class MoneyTransfer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String senderAccountNumber;
	private String receiverAccountNumber;
	double amount;
	double fee;


//	@NotNull(message="Current account number of the MoneyTransfer's sender")
//	@Pattern(regexp="[0-9_-]+", message="The account number must be 12 digits")
	public String getSenderAccountNumber() { return senderAccountNumber; }
	public void setSenderAccountNumber(String senderAccountNumber) { this.senderAccountNumber = senderAccountNumber; }

//	@NotNull(message="Current account number of the MoneyTransfer's receiverr")
//	@Pattern(regexp="[0-9_-]+", message="The account number must be 12 digits")
	public String getReceiverAccountNumber() { return receiverAccountNumber; }
	public void setReceiverAccountNumber(String receiverAccountNumber) { this.receiverAccountNumber = receiverAccountNumber; }

//	@NotNull(message="Value of the currency to be transfer from a sender account to a receiver account.")
	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount; }

//	@NotNull(message="Value of the currency to charged for the service provided. If different currency both accounts have to be charged." )
	public double getFee() { return fee; }
	public void setFee(double fee) { this.fee = fee; }

	public MoneyTransfer() {
	}
	
	public MoneyTransfer(String sender, String  receiver, double amount, double fee) {
		this.senderAccountNumber = sender;
		this.receiverAccountNumber = receiver;
		this.amount = amount;
		this.fee = fee;
	}
	@Override
	public String toString() {
		return "MoneyTransfer [senderAccountNumber=" + senderAccountNumber + ", receiverAccountNumber="
				+ receiverAccountNumber + ", amount=" + amount + ", fee=" + fee + "]";
	}
	
	

}
