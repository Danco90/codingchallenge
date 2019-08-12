package com.daniele.project.restmoneytx.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "bankAccounts", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
@NamedQueries({
	@NamedQuery(name = "BankAccount.findAll", query = "SELECT b FROM BankAccount b"),
	@NamedQuery(name = "BankAccount.findByAcctNum", query = "SELECT b FROM BankAccount b WHERE b.acctNumber = :acctNumber")
})
public class BankAccount implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "OWNER_NAME")
	private String ownerName;
	
	@Column(name = "ACCT_NUM")
	private long acctNumber;
	
	@Column(name = "BALANCE")
	private double balance;
	
	public BankAccount() {
		
	}
	
	public BankAccount(String ownerName, long acctNumber) {
		this.ownerName = ownerName;
		this.acctNumber = acctNumber;
	}
	
	public BankAccount(long id, String ownerName, long acctNumber) {
		this(ownerName, acctNumber);
		this.id = id;
	}
	
	public BankAccount(String ownerName, long acctNumber, double balance) {
		this(ownerName, acctNumber);
		this.balance = balance;
	}
	
	public BankAccount(long id, String ownerName, long acctNumber, double balance) {
		this(id, ownerName, acctNumber);
		this.balance = balance;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "BankAccountDAO [id=" + id + ", ownerName=" + ownerName + ", acctNumber=" + acctNumber + ", balance="
				+ balance + "]";
	}
	
	

}
