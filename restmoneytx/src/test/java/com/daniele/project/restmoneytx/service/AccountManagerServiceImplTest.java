package com.daniele.project.restmoneytx.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.daniele.project.restmoneytx.model.AccountConversionBean;
import com.daniele.project.restmoneytx.model.BankAccount;
import com.daniele.project.restmoneytx.model.MoneyTransfer;
import com.daniele.project.restmoneytx.model.TransferOutcome;

public class AccountManagerServiceImplTest {
	
	 EntityManagerFactory emf;
	 
	 AccountManagerService service;
	 
	 @Before
	 public void setUp() {
		 emf = Persistence.createEntityManagerFactory("restapi_PU"); 
	 }
	 
	 @After
	 public void tearDown() {
		 emf.close();
	 }
	 
	 @Test
	 public void testDemoConcurrencyUsingSeparateInstancesOfEntityManagerForMoreThreads() throws Exception {
		 	service = new AccountManagerServiceImpl(emf);
			ExecutorService es = Executors.newFixedThreadPool(2);
			List<Future<?>> futures = new ArrayList<>();
			Future<?> f1 = es.submit(() -> {
				return service.create(new BankAccount("TestUser1", 1, 100.0));
		    });
			
			Future<?> f2 = es.submit(() -> {
				return service.create(new BankAccount("TestUser2", 2, 200.0));
		    });
			
			futures.add(f1);
		    futures.add(f2);
		    try {
		          for (Future<?> future : futures) {
		              future.get();
		          }
		    } catch (Exception e) {
		    		e.printStackTrace();
		    }
		    
		    MoneyTransfer mt = new MoneyTransfer();
		    mt.setSenderAccountNumber("1");
		    mt.setReceiverAccountNumber("2");
		    mt.setAmount(50.0);
		    mt.setFee(2.0);
		    
		    Future<?> f3 = es.submit(() -> {
		        return service.transfer(mt);
		    });
		    futures.add(f3);
		   
		    BankAccount from = new BankAccount("TestUser1",1,100.0);
		    Future<?> f4 = es.submit(() -> {
		    		return service.withdraw(from, 10.0, 0.5);
		    });
		    futures.add(f4);
		    Future<?> f5 = es.submit(() -> {
		    		return service.withdraw(from, 10.0, 0.5);
		    });
		    futures.add(f5);
		    
		    try {
		          for (Future<?> future : futures) {
		              future.get();
		          }
		    } catch (Exception e) {
		       System.out.println(Thread.currentThread() + ", threw exception "+e);
		    }
		    
		    Future<?> f6 = es.submit(() -> {
	    			return service.removeAll();
		    });
		    Future<?> f7 = es.submit(() -> {
		    		return service.removeAll();
		    });
		    Future<?> f8 = es.submit(() -> {
    				return service.getAll();
		    });
		    
		    futures.add(f6);
		    futures.add(f7);
		    futures.add(f8);
		    try {
		          for (Future<?> future : futures) {
		              future.get();
		          }
		    } catch (Exception e) {
		        System.out.println(Thread.currentThread() + ", threw exception "+e);
		    }
		    es.shutdown();   
	 }

	 @Test
	 public void createTest() {
		BankAccount account = new BankAccount("TestUser1", 1, 100.0);
	 	service = new AccountManagerServiceImpl(emf);
		BankAccount out=null;
		try {
			out = service.create(account);
		} catch (Exception e) { e.printStackTrace(); }
		assertNotNull("Should return the created user", out);
		 //Clear the data
		long deletions=0;
		try {
			deletions = service.removeAll();
		} catch (Exception e) { e.printStackTrace();
		}
		assertNotNull("Should return the number of deletions", deletions);
	 }
		
		 
	 @Test
	 public void getAllTest() {
		service = new AccountManagerServiceImpl(emf);
		BankAccount acct1 = new BankAccount("TestUser1", 1, 100.0);
		BankAccount acct2 = new BankAccount("TestUser2", 2, 200.0);
		try {
			service.create(acct1);
			service.create(acct2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<BankAccount> out = null;
		try {
			out = service.getAll();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		assertNotNull("Should return a list", out);
		assertEquals(out.size(),2);
		 //Clear the data
		long deletions=0;
		try {
			deletions = service.removeAll();
		} catch (Exception e) { e.printStackTrace(); }
		assertNotNull("Should return the number of deletions", deletions);
		assertEquals(deletions, 2);
	 }

		
	@Test
	public void depositTest() {
		service = new AccountManagerServiceImpl(emf);
		BankAccount acct = new BankAccount("TestUser3", 3, 100.0);
		try {
			service.create(acct);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		BankAccount out = null;
		try {
			out = service.deposit(acct, 20.0);
		} catch (Exception e) { e.printStackTrace(); }
		assertNotNull("Should return the updated account", out);
		assertEquals(String.valueOf(out.getBalance()), "120.0");
		 //Clear the data
		long deletions=0;
		try {
			deletions = service.removeAll();
		} catch (Exception e) {  e.printStackTrace(); }
		assertNotNull("Should return the number of deletions", deletions);
		assertEquals(1,deletions);
		
	}
		
	@Test
	public void withdrawTest()  {
		service = new AccountManagerServiceImpl(emf);
		BankAccount acct1 = new BankAccount("TestUser1", 1, 100.0);
		try {
			service.create(acct1);
		} catch (Exception e) { e.printStackTrace(); }
		BankAccount out = null;
		try {
			out = service.withdraw(acct1, 20.0, 0.5);
		} catch (Exception e) { e.printStackTrace(); }
		assertNotNull("Should return the updated account", out);
		assertEquals("79.5",String.valueOf(out.getBalance()));
		
		try {
			service.removeByAcctNum(1);
		} catch (Exception e) { e.printStackTrace(); }
		 //Clear the data
		long deletions=0;
		try {
			deletions = service.removeAll();
		} catch (Exception e) {  e.printStackTrace(); }
		assertNotNull("Should return the number of deletions", deletions);
		assertEquals(1, deletions);
	}
	 
	@Test
	public void transferTest()  {
		service = new AccountManagerServiceImpl(emf);
		BankAccount creation1=null;
		BankAccount creation2=null;
		try {
			creation1 = service.create(new BankAccount("TestUser1", 123, 100.0));
			creation2 = service.create(new BankAccount("TestUser2", 124, 200.0));
		} catch (Exception e) { e.printStackTrace(); }
		 
		List<BankAccount> list = null;
		try {
			list = service.getAll();
		} catch (Exception e1) { 	e1.printStackTrace(); }
		assertNotNull("Should return a list", list);
		assertEquals(2,list.size());
		
		MoneyTransfer mt = new MoneyTransfer();
	    mt.setSenderAccountNumber("123");
	    mt.setReceiverAccountNumber("124");
	    mt.setAmount(50.0);
	    mt.setFee(2.0);
	    
	    TransferOutcome out=null;
		try {
			out = service.transfer(mt);
		} catch (Exception e) { e.printStackTrace(); }
		assertNotNull("Should return the transfer outcome bean", out);
		
		AccountConversionBean exp_sender = new AccountConversionBean(creation1.getId(),"TestUser1", 123, 48.0);
		AccountConversionBean exp_receiver = new AccountConversionBean(creation2.getId(),"TestUser2", 124, 250.0 );
		
		TransferOutcome out_expected = new TransferOutcome(50.0, 2.0, exp_sender, exp_receiver);
		assertEquals(out_expected, out);
		
		 //Clear the data
		long deletions=0;
		try {
			deletions = service.removeAll();
		} catch (Exception e) { e.printStackTrace(); }
		assertNotNull("Should return the number of deletions", deletions);
		assertEquals(2,deletions);
	}
	
	
	@Test
	public void removeByAcctNumTest() {
        service = new AccountManagerServiceImpl(emf);
		try {
			service.create(new BankAccount("TestUser1", 1, 100.0));
		} catch (Exception e) { e.printStackTrace(); }
		
		List<BankAccount> out = null;
		try {
			out = service.getAll();
		} catch (Exception e1) {  e1.printStackTrace(); }

		assertNotNull("Should return a list", out);
		assertEquals(1,out.size());
		 //Clear the data
		try {
			service.removeAll();
		} catch (Exception e) { e.printStackTrace(); }
		
		List<BankAccount> out2 = null;
		try {
			out2 = service.getAll();
		} catch (Exception e) { e.printStackTrace(); }
		
		assertNotNull("Should return a list", out2);
		assertEquals(0,out2.size());
	}
	
		
	@Test
	public void removeAllTest() throws Exception {
        service = new AccountManagerServiceImpl(emf);

		try {
			service.create(new BankAccount("TestUser1", 1, 100.0));
			service.create(new BankAccount("TestUser2", 2, 200.0));;
		} catch (Exception e) { e.printStackTrace(); }
		 
		List<BankAccount> out = null;
		try {
			out = service.getAll();
		} catch (Exception e1) { e1.printStackTrace(); }
		assertNotNull("Should return a list", out);
		assertEquals(2,out.size());
		
		try {
			service.removeAll();
		} catch (Exception e) { e.printStackTrace(); }
		List<BankAccount> resultList = service.getAll();
		assertNotNull("Should return an empty list", resultList);
		assertEquals(0,resultList.size());
	}
	 
	

}
