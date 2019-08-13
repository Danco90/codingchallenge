package com.daniele.project.restmoneytx.service;

import java.util.List;
import com.daniele.project.restmoneytx.exception.AccountMovementException;
import com.daniele.project.restmoneytx.model.BankAccount;
import com.daniele.project.restmoneytx.model.MoneyTransfer;
import com.daniele.project.restmoneytx.model.TransferOutcome;

public interface AccountManagerService {
	
	BankAccount create(BankAccount account) throws Exception;  
	
	List<BankAccount> getAll();

	BankAccount deposit(BankAccount to, double amount) throws Exception/*AccountMovementException*/;
	
	BankAccount withdraw(BankAccount from, double amount, double fee) throws Exception/*AccountMovementException*/;
	
	TransferOutcome transfer(MoneyTransfer transfer) throws AccountMovementException;

	void removeByAcctNum(long AcctNum) throws Exception;
	
	long removeAll() throws Exception;  
	
}
