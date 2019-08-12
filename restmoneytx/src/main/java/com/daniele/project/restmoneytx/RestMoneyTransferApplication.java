package com.daniele.project.restmoneytx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.daniele.project.restmoneytx.model.BankAccount;
import com.daniele.project.restmoneytx.model.MoneyTransfer;
import com.daniele.project.restmoneytx.service.AccountManagerServiceImpl;

public class RestMoneyTransferApplication {

	public static void main(String[] args) throws Exception {
		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("restapi_PU");
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		List<Future<?>> futures = new ArrayList<>();
		Future<?> f1 = es.submit(() -> {
	          return runTask(emf, 1, "TestUser1", 1, 100.0);
	    });
		
		Future<?> f2 = es.submit(() -> {
	          return runTask(emf, 1, "TestUser2", 2, 200.0);
	    });
		
		futures.add(f1);
	    futures.add(f2);
	    
	    try {
	          for (Future<?> future : futures) {
	              future.get();
	          }
	    } catch (Exception e) {
	    		System.out.println(Thread.currentThread() + ","+e);
	          //e.printStackTrace();
	    }
	    
	    MoneyTransfer mt = new MoneyTransfer();
	    mt.setSenderAccountNumber("1");
	    mt.setReceiverAccountNumber("2");
	    mt.setAmount(50.0);
	    mt.setFee(2.0);
	    
	    
	    Future<?> f3 = es.submit(() -> {
	          return accountMovements(emf, mt);
	    });
	    futures.add(f3);
	   
	    BankAccount from = new BankAccount("TestUser1",1);
	    Future<?> f4 = es.submit(() -> {
	    		return withdrawal(emf, from, 10.0, 0.5);
	    });
	    futures.add(f4);
	    Future<?> f5 = es.submit(() -> {
    		return withdrawal(emf, from, 10.0, 0.5);
	    });
	    futures.add(f5);
	    
	    try {
	          for (Future<?> future : futures) {
	              future.get();
	          }
	    } catch (Exception e) {
	          //e.printStackTrace();
	    		System.out.println(Thread.currentThread() + ", threw exception "+e.getMessage());
	    }
	    
	    es.shutdown();
	    emf.close();

	}
	
	private static String runTask(EntityManagerFactory emf, long id, String ownerName, long acctNumber, double balance) {
		  System.out.printf(Thread.currentThread()+", persisting id: %s ownerName:%s acctNumber: %s balance:%s%n", id, ownerName, acctNumber, balance);
	      EntityManager em = emf.createEntityManager();
	      em.getTransaction().begin();
	      em.persist(createNewMyEntity(id, ownerName, acctNumber, balance));
	      em.getTransaction().commit();
	      em.close();

	      return Thread.currentThread() + " done executing id: " + id;
	 }
	
	 private static BankAccount createNewMyEntity(long id, String ownerName, long acctNumber, double balance) {
		  BankAccount entity = new BankAccount();
//	      entity.setId(id);
	      entity.setOwnerName(ownerName);
	      entity.setAcctNumber(acctNumber);
	      entity.setBalance(balance);
	      return entity;
	  }
	 
	 private static String withdrawal(EntityManagerFactory emf, BankAccount from, double amount, double fee) {
	      System.out.println(Thread.currentThread()+" was able to perform the account withdrawal : \n" + from + "("+System.currentTimeMillis()+")");
	      EntityManager em = emf.createEntityManager();
	      AccountManagerServiceImpl manager = new AccountManagerServiceImpl(em);
	      try {
			manager.withdraw(from, amount, fee);
	      } catch (Exception e) {
//			e.printStackTrace();
	    	  	System.out.println(Thread.currentThread() + ", threw exception "+e.getMessage());
	      }

	      return Thread.currentThread() +" done executing account withdrawal" ;
	  }
	 
	 private static String accountMovements(EntityManagerFactory emf, MoneyTransfer mt) {
	      System.out.println(Thread.currentThread()+" attempting to perform the account movement : \n" + mt);
	      EntityManager em = emf.createEntityManager();
	      AccountManagerServiceImpl manager = new AccountManagerServiceImpl(em);
	      
	      try {
			manager.transfer(mt);
	      } catch (Exception e) {
			//e.printStackTrace();
			System.out.println(Thread.currentThread() + " threw exception "+e.getMessage());
	      }

	      return Thread.currentThread() +" done executing accountMovements" ;
	  }

}
