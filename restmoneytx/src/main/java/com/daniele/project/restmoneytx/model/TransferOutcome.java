package com.daniele.project.restmoneytx.model;

/**
 * 
 * @author mdaniele
 * @update 12/18/2019
 * @version 0.6.1
 */
public class TransferOutcome {

	private double amount;
	
	private double fee;
	
	private AccountConversionBean senderAccount;
	
	private AccountConversionBean receiverAccount;
	
	public TransferOutcome(double amount, double fee) {
		this.amount = amount;
		this.fee = fee;
	
	}

	public TransferOutcome(double amount, double fee, AccountConversionBean senderAccount, AccountConversionBean receiverAccount) {
		this.amount = amount;
		this.fee = fee;
		this.senderAccount = senderAccount;
		this.receiverAccount = receiverAccount;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public AccountConversionBean getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(AccountConversionBean senderAccount) {
		this.senderAccount = senderAccount;
	}

	public AccountConversionBean getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(AccountConversionBean receiverAccount) {
		this.receiverAccount = receiverAccount;
	}
	
	
	
	@Override
	public String toString() {
		return "TransferOutcome [amount=" + amount + ", fee=" + fee + ", senderAccount=" + senderAccount
				+ ", receiverAccount=" + receiverAccount + "]";
	}

	@Override 
	public boolean equals(Object obj) {
		if (obj == this) { return true; } 
		if (obj == null || obj.getClass() != this.getClass()) 
		{ return false; } 
		TransferOutcome guest = (TransferOutcome) obj; 
		return 
		   (senderAccount != null && senderAccount.equals(guest.getSenderAccount())) && 
		   (receiverAccount != null && receiverAccount.equals(guest.getReceiverAccount())) && 
		   amount == guest.amount &&
		   fee == guest.fee;
	}


}
