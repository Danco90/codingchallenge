package com.daniele.project.restmoneytx.rest;

import static com.daniele.project.restmoneytx.mapper.TransferMapper.mapToDAO;
import static com.daniele.project.restmoneytx.mapper.TransferMapper.mapToResponse;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daniele.project.restmoneytx.model.BankAccount;
import com.daniele.project.restmoneytx.model.BankAccountCreation;
import com.daniele.project.restmoneytx.model.MoneyTransfer;
import com.daniele.project.restmoneytx.model.TransferOutcome;
import com.daniele.project.restmoneytx.service.AccountManagerService;
import com.daniele.project.restmoneytx.service.AccountManagerServiceImpl;

/**
 * Root resource (exposed at "bankAccounts" path)
 */
@RequestScoped
@Path("bankAccounts")
public class MyResource {
	
	private static final Logger logger = LoggerFactory.getLogger(MyResource.class);

//	@PersistenceContext(unitName = "test")
//    private EntityManager em;	
//	private EntityManagerFactory entityManagerFactory = Persistence
//													.createEntityManagerFactory("test");;
//    private EntityManager entityManager= entityManagerFactory.createEntityManager();

	 
//	AccountManager manager = new AccountManagerImpl(entityManager);//TODO to be moved 

	@Inject
	AccountManagerService service;

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<BankAccount> bankAccounts = service.getAll();
        return Response.status(Status.OK)
                .entity(new GenericEntity<List<BankAccount>>(bankAccounts) {
                }).build();
    }
 
    /**
	 * 
	 * @param account
	 * @throws Exception 
	 * @description POST '/newAcct' : create new account
	 */
	@POST
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBankAccount(BankAccountCreation account) throws Exception{

		BankAccount entity = service.create(mapToDAO(account));
		BankAccountCreation result = mapToResponse(entity);
		return Response
			      .status(Response.Status.OK)
			      .entity(result)
			      .build();
	}
	
	@PUT
	@Path( value = "/deposit/{amount}")	
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response deposit(@PathParam("amount") Double amount, BankAccount account) throws Exception{
		BankAccount acct = service.deposit(account, amount);
		if (null == acct) {
			logger.error("No Account updated for ID "+account.getId() , Response.Status.NOT_FOUND);
			return Response.status(Response.Status.NOT_FOUND)
						.entity(acct)
						.build();
		}
		return Response.status(Response.Status.OK)
			      .entity(acct)
			      .build();
	}
	
	@PUT
	@Path( value = "/withdraw/{amount}/{fee}")	
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response withdraw(@PathParam("amount") Double amount,@PathParam("fee") double fee, BankAccount account) throws Exception{
		BankAccount acct = service.withdraw(account, amount, fee);
		if (null == acct) {
			logger.error("No Account updated for ID "+account.getId() , Response.Status.NOT_FOUND);
			return Response.status(Response.Status.NOT_FOUND)
						.entity(acct)
						.build();
		}
		return Response.status(Response.Status.OK)
			      .entity(acct)
			      .build();
	}
	
	@PUT
	@Path( value = "/transfer")	
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response transfer(MoneyTransfer account) throws Exception{
		TransferOutcome result = service.transfer(account);
		if (null == result) {
			logger.error("No Account updated for "+account, Response.Status.NOT_FOUND);
			return Response.status(Response.Status.NOT_FOUND)
						.entity(result)
						.build();
		}
		return Response.status(Response.Status.OK)
			      .entity(result)
			      .build();
	}
	
}
