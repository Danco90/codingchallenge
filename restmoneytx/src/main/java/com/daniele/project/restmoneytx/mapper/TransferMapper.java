package com.daniele.project.restmoneytx.mapper;

import com.daniele.project.restmoneytx.model.AccountConversionBean;
import com.daniele.project.restmoneytx.model.BankAccount;
import com.daniele.project.restmoneytx.model.BankAccountCreation;
import com.daniele.project.restmoneytx.model.TransferOutcome;

/**
 * 
 * @author mdaniele
 * @update 12/08/2019
 * @version 0.9.1
 */
//@Component
public class TransferMapper {
		
	public static TransferOutcome mapToTransferOutcome(BankAccount sender, BankAccount receiver, double amount, double fee) {
		
		TransferOutcome out = new TransferOutcome(amount, fee);
		out.setSenderAccount(mapToAccountConversionBean(sender));
		out.setReceiverAccount(mapToAccountConversionBean(receiver));
		return out;
	}
	
	private static AccountConversionBean mapToAccountConversionBean(BankAccount in) {
		
		AccountConversionBean out = new AccountConversionBean();
		out.setOwnerName(in.getOwnerName());
		out.setAcctNumber(in.getAcctNumber());
		out.setBalance(in.getBalance());
		
		return out;
		
	}
	
	public static BankAccountCreation mapToResponse(BankAccount input) {
		BankAccountCreation out = new BankAccountCreation();
		out.setId(input.getId());
		out.setName(input.getOwnerName());
		out.setNumber(input.getAcctNumber());
		out.setBalance(input.getBalance());
		
		return out;
	}
	
	public static BankAccount mapToDAO(BankAccountCreation input) {
		BankAccount out = new BankAccount(
				input.getId(), input.getName(),
				input.getNumber(), input.getBalance());
		
		return out;
	}
	
//	public static DebitAmount mapToDebitAmount(AccountDAO input, double amount) {
//		
//		DebitAmount out = new DebitAmount();
//		out.setSenderName(input.getOwnerName());
//		out.setSenderAcctNum(input.getAcctNumber());
//		out.setAmount(amount);
//		
//		return out;
//	}
//	
//	public CreditAmount mapToCreditAmount(AccountDAO input, double amount) {
//		
//		CreditAmount out = new CreditAmount();
//		out.setSenderName(input.getOwnerName());
//		out.setSenderAcctNum(input.getAcctNumber());
//		out.setAmount(amount);
//		
//		return out;
//	}
	
//	public AccountRevision mapToAccountRevision(double balance) {
//		
//		AccountRevision out = new AccountRevision();
//		out.setBalance(balance);
//		
//		return out;
//	}
	
}
