package com.daniele.project.restmoneytx.rest;

import static com.daniele.project.restmoneytx.mapper.TransferMapper.mapToDAO;
import static com.daniele.project.restmoneytx.mapper.TransferMapper.mapToResponse;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import org.apache.log4j.Logger;
import com.daniele.project.restmoneytx.model.BankAccount;
import com.daniele.project.restmoneytx.model.BankAccountCreation;
import com.daniele.project.restmoneytx.model.MoneyTransfer;
import com.daniele.project.restmoneytx.model.TransferOutcome;
import com.daniele.project.restmoneytx.service.AccountManagerService;

/**
 * Root resource (exposed at "bankAccounts" path)
 */
@RequestScoped
@Path("bankAccounts")
public class BankAccountsResource {
	
	private static org.apache.log4j.Logger logger 
    = Logger.getLogger(BankAccountsResource.class);

	@Inject
	AccountManagerService service ;

	public BankAccountsResource() {
	}
   
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws Exception {
		logger.info(" getAll start");
        List<BankAccount> bankAccounts = service.getAll();
        logger.info(" done executing getAll");
        return Response.status(Status.OK)
                .entity(new GenericEntity<List<BankAccount>>(bankAccounts) {
                }).build();
    }
 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBankAccount(BankAccountCreation account) throws Exception{
		logger.info(" createBankAccount start");
		BankAccount entity = service.create(mapToDAO(account));
		BankAccountCreation result = mapToResponse(entity);
		logger.info(" done executing getAll :");
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
		logger.info(" deposit start");
		BankAccount acct = service.deposit(account, amount);
		if (null == acct) {
			logger.error(" No Account updated for ID "+account.getId() + ", " +Response.Status.NOT_FOUND);
			return Response.status(Response.Status.NOT_FOUND)
						.entity(acct)
						.build();
		}
		logger.info(" done executing deposit :"+acct);
		return Response.status(Response.Status.OK)
			      .entity(acct)
			      .build();
	}
	
	@PUT
	@Path( value = "/withdraw/{amount}/{fee}")	
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response withdraw(@PathParam("amount") Double amount,@PathParam("fee") double fee, BankAccount account) throws Exception{
		logger.info(" withdraw start");
		BankAccount acct = service.withdraw(account, amount, fee);
		if (null == acct) {
			logger.error(" No Account updated for ID "+account.getId() + ", " +Response.Status.NOT_FOUND);
			
			return Response.status(Response.Status.NOT_FOUND)
						.entity(acct)
						.build();
		}
		logger.info(" done executing withdraw :"+acct);
		return Response.status(Response.Status.OK)
			      .entity(acct)
			      .build();
	}
	
	@PUT
	@Path( value = "/transfer")	
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response transfer(MoneyTransfer account) throws Exception{
		logger.info(" transfer start");
		TransferOutcome result = service.transfer(account);
		if (null == result) {
			logger.error(" No Account updated for ID "+account + ", " +Response.Status.NOT_FOUND);
			return Response.status(Response.Status.NOT_FOUND)
						.entity(result)
						.build();
		}
		logger.info(" done executing transfer :"+result);
		return Response.status(Response.Status.OK)
			      .entity(result)
			      .build();
	}
	
	@DELETE
	@Path( value = "/{acctNum}")	
	public Response remove(@PathParam("acctNum") String acctNum) throws Exception{
		logger.info(" remove");
		service.removeByAcctNum(Long.parseLong(acctNum));
		logger.info(" done executing remove ");
		return Response.status(Response.Status.OK)
			      .build();
	}
	
	@DELETE
	@Path( value = "")	
    @Produces(MediaType.TEXT_PLAIN)
	public Response removeAll() throws Exception{
		logger.info(" removeAll start");
		Long deletions = service.removeAll();
		logger.info(" done executing removeAll : deleted "+deletions+" accounts");
		
		return Response.status(Response.Status.OK)
				  .entity(deletions)
			      .build();
	}

}
