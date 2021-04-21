package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.AccountDTO;
import com.revature.dto.ClientDTO;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController implements Controller {
	
	private Logger logger = LoggerFactory.getLogger(ClientController.class);
	private ClientService clientService;
	
	public ClientController() {
		this.clientService = new ClientService();
	}
	
	private Handler addClient = ctx -> {
		ClientDTO clientDTO = ctx.bodyAsClass(ClientDTO.class);
		
		Client insertedClient = clientService.addClient(clientDTO);
		
		
		ctx.status(201);
		ctx.json(insertedClient);
	};
	
	private Handler getAllClients = ctx -> {
		List<Client> listOfClients = clientService.getClients();
		ctx.status(201);
		ctx.json(listOfClients);
	};
	
	private Handler getClientById = ctx -> {
		String id = ctx.pathParam("id");
		Client selectedClient = clientService.getClientById(id);
		ctx.status(201);
		ctx.json(selectedClient);
	};
	
	private Handler updateClientById = ctx -> {
		String id = ctx.pathParam("id");
		Client selectedClient = clientService.getClientById(id);
		ClientDTO clientDTO = ctx.bodyAsClass(ClientDTO.class);
		
		clientService.updateClientById(selectedClient, clientDTO);
		ctx.status(201);
	};
	
	private Handler deleteClientById = ctx -> {
		String id = ctx.pathParam("id");
		
		clientService.deleteClientById(id);
		ctx.status(201);
	};
	
	private Handler addAccountToClientById = ctx -> {
		AccountDTO accountDTO = ctx.bodyAsClass(AccountDTO.class);
		Account insertAccount = clientService.addAccount(accountDTO);
		ctx.status(201);
		ctx.json(insertAccount);
	};
	
	private Handler getAccountsForClientById = ctx -> {
		String id = ctx.pathParam("id");
		
		List<Account> listOfAccounts = clientService.getAccountsWithClientId(id);
		ctx.status(201);
		ctx.json(listOfAccounts);
	};
	
	private Handler getAllAccountsForClientByIdBetween = ctx -> {
		String client_id = ctx.queryParam("id");
		String lowerBalance = ctx.queryParam("Lower_Balance");
		String higherBalance = ctx.queryParam("Higher_Balance");
		
		List<Account> listOfAccounts = clientService.getAccountsForClientByIdBetween(client_id, lowerBalance, higherBalance);
		ctx.status(201);
		ctx.json(listOfAccounts);
	};
	
	private Handler getAccountByIdBelongingToClientById = ctx -> {
		String id = ctx.queryParam("id");
		String client_id = ctx.queryParam("client_id");
		
		Account account = clientService.getAccountByIdAndByClientId(id, client_id);
		ctx.status(201);
		ctx.json(account);
	};
	
	private Handler updateAccountByIdBelongingToClientById = ctx -> {
		String id = ctx.queryParam("id");
		String client_id = ctx.queryParam("client_id");
		
		Account account = clientService.getAccountByIdAndByClientId(id, client_id);
		AccountDTO accountDTO = ctx.bodyAsClass(AccountDTO.class);
		
		clientService.updateAccountByIdAndByClientId(account, accountDTO);
		ctx.status(201);
	};
	
	private Handler deleteAccountByIdBelongingToClientById = ctx -> {
		String id = ctx.queryParam("id");
		String client_id = ctx.queryParam("client_id");
		clientService.deleteAccountByIdAndByClientId(id, client_id);
		ctx.status(201);
	};
	
	@Override
	public void mapEndPoints(Javalin app) {
		app.post("/clients", addClient);
		app.get("/clients/1", getAllClients);
		app.get("/clients/:id", getClientById);
		app.put("/clients/:id", updateClientById);
		app.delete("/clients/:id", deleteClientById);
		app.post("/accounts", addAccountToClientById);
		app.get("/accounts/:id", getAccountsForClientById);
		app.get("/accounts/?", getAllAccountsForClientByIdBetween);
		app.get("/accounts/2/?id", getAccountByIdBelongingToClientById);
		app.put("/accounts/?", updateAccountByIdBelongingToClientById);
		app.delete("/accounts/?", deleteAccountByIdBelongingToClientById);
	}
}
