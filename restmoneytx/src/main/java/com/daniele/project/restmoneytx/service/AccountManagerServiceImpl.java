package com.daniele.project.restmoneytx.service;

import static com.daniele.project.restmoneytx.mapper.TransferMapper.mapToTransferOutcome;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daniele.project.restmoneytx.exception.AccountMovementException;
import com.daniele.project.restmoneytx.model.BankAccount;
import com.daniele.project.restmoneytx.model.MoneyTransfer;
import com.daniele.project.restmoneytx.model.TransferOutcome;

@Stateless
public class AccountManagerServiceImpl implements AccountManagerService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountManagerServiceImpl.class);

    @PersistenceContext(unitName = "restapi_PU")
    EntityManager em;
	
//	@PersistenceUnit(name="restapi_PU")
//  private EntityManagerFactory emf;
	
    public AccountManagerServiceImpl(){
    	
    }
   
    private BankAccount findByAcctNum(long acctNum) {
    		EntityManagerFactory emf = Persistence.createEntityManagerFactory("restapi_PU");
		EntityManager em = emf.createEntityManager();
		TypedQuery<BankAccount> query = em.createNamedQuery("BankAccount.findByAcctNum", BankAccount.class);
		return query.setParameter("acctNumber", acctNum).getSingleResult();
    }
    
    public List<BankAccount> getAll() {
    		EntityManagerFactory emf = Persistence.createEntityManagerFactory("restapi_PU");
    		EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("BankAccount.findAll", BankAccount.class).getResultList();
    }

	public BankAccount deposit(BankAccount to, double amount) throws AccountMovementException {
		logger.info("depositing the amount of "+ amount + " from banking account :"+to);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("restapi_PU");
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
		if (amount < 0) {
			logger.error("Error: Deposit amount is invalid.");
            throw new AccountMovementException("Error: Withdraw amount is invalid. It could never be deposited",String.valueOf(to.getAcctNumber()));         
        }else {
        		BankAccount account = null;
        		try {
        			tx.begin();
        			account = em.find(BankAccount.class, findByAcctNum(to.getAcctNumber()));
                account.setBalance(account.getBalance()+amount);
                em.persist(account);
                tx.commit();
        		} catch(Exception e) {
        			if (tx != null && tx.isActive()) {
        	            tx.rollback();
        	        }
        			throw e;
        		} finally {
        			if(em != null && em.isOpen()) {
        	            em.close();
        	        }
        		}
          
          	return account;
        }
	}

	public BankAccount withdraw(BankAccount from, double amount, double fee) throws AccountMovementException {
		logger.info("withdrawing the amount of "+ amount + "(fee="+fee+") from banking account :"+ from);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("restapi_PU");
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
		amount += fee;
        if (amount < 0) {
        		logger.error("Error: Withdraw amount is invalid. It could never be deposited on acctNum="+from.getAcctNumber());
        		throw new AccountMovementException("Error: Withdraw amount is invalid. It could never be deposited",String.valueOf(from.getAcctNumber()));      
        }else if (amount > from.getBalance()) {
        		logger.error("Error: Deposit amount is invalid on acctNum="+from.getAcctNumber());
        		throw new AccountMovementException("Error: Insufficient funds", String.valueOf(from.getAcctNumber()));     
        }else {
        		BankAccount account = null;
        		try {
        			tx.begin();
    				account = em.find(BankAccount.class, findByAcctNum(from.getAcctNumber()).getId());
    				//TODO handle if account = null
    				account.setBalance((account.getBalance())-amount); 
    				em.persist(account); 
    				tx.commit();
        		} catch(Exception e) {
        			if (tx != null && tx.isActive()) {
        	            tx.rollback();
        	        }
        			throw e;
        		} finally {
        			if(em != null && em.isOpen()) {
        	            em.close();
        	        }
        		}
        		
        		return from;
        }
	}
	
	public TransferOutcome transfer(MoneyTransfer transfer) throws AccountMovementException{
		logger.info("trasfering amount : " + transfer);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("restapi_PU");
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
		BankAccount senderAccount = em.find(BankAccount.class, findByAcctNum(Long.parseLong(transfer.getSenderAccountNumber())).getId());			
		if(senderAccount==null) {
			 throw new AccountMovementException("Error: Sender Account number is invalid. Operation denied ",transfer.getSenderAccountNumber());             
		}
		
		if (transfer.getAmount() < 0) 
		{   logger.error("Error: Deposit amount is invalid.");
            throw new AccountMovementException("Error: Transfer amount is invalid. Operation denied ",transfer.getSenderAccountNumber());      
        }else if (transfer.getAmount() > senderAccount.getBalance()) {
          	throw new AccountMovementException("Error: Insufficient funds for Sender account ", transfer.getSenderAccountNumber());     
        }
		
		BankAccount receiverAccount = null;
		try {
			tx.begin();
			receiverAccount = em.find(BankAccount.class, findByAcctNum(Long.parseLong(transfer.getReceiverAccountNumber())).getId());			
			if(receiverAccount==null) {
				 throw new AccountMovementException("Error: Receiver Account number is invalid. Operation denied ",transfer.getReceiverAccountNumber());             
			}
			if (transfer.getAmount() < 0) 
			{
				logger.error("Error: Deposit amount is invalid.");
	            throw new AccountMovementException("Error: Transfer amount is invalid. Operation denied ",transfer.getSenderAccountNumber());      
	        }else if (transfer.getAmount() > senderAccount.getBalance()) {
	          	throw new AccountMovementException("Error: Insufficient funds for Sender account ", transfer.getSenderAccountNumber());     
	        }
			double transfAmount = transfer.getAmount() + transfer.getFee(); 
			senderAccount.setBalance((senderAccount.getBalance())-transfAmount);
			receiverAccount.setBalance((receiverAccount.getBalance())+transfer.getAmount());
			
			em.persist(senderAccount);
//			tx.commit();
			em.persist(receiverAccount); 
			tx.commit();
			
		} catch(Exception e) {
			if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }
			throw e;
		} finally {
			if(em != null && em.isOpen()) {
	            em.close();
	        }
		}

		return mapToTransferOutcome(senderAccount, receiverAccount, transfer.getAmount(), transfer.getFee());
	}

	public BankAccount create(BankAccount account) throws Exception {
		logger.info("creating banking account: "+account);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("restapi_PU");
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(account);
			tx.commit();
		} catch(Exception e) {
			if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }
//			logger.error("",e);
			throw e;
		} finally {
			if(em != null && em.isOpen()) {
	            em.close();
	        }
		}
        return account;
	}
	
	 
    public AccountManagerServiceImpl(EntityManager entityManager) {
    		em = entityManager;
    }

}
