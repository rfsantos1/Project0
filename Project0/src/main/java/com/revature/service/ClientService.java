package com.revature.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.revature.dto.AccountDTO;
import com.revature.dto.ClientDTO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.AddClientException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.BrokenBalanceException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.repo.ClientRepo;
import com.revature.util.ConnectionUtil;

public class ClientService {
	private ClientRepo clientRepo;
	
	public ClientService(){
		this.clientRepo = new ClientRepo();
	}
	
	public ClientService(ClientRepo clientRepo) {
		this.clientRepo = clientRepo;
	}
	
	public List<Client> getClients(){
		return clientRepo.getClients();
	}
	
	public List<Account> getAccounts(){
		return clientRepo.getAccounts();
	}
	
	public Client getClientById(int id) throws DatabaseException, ClientNotFoundException {
		return clientRepo.getClientById(id);
	}
	
	public Client getClientById(String id) throws DatabaseException, ClientNotFoundException {
		int parsedIntId = Integer.parseInt(id);
		return clientRepo.getClientById(parsedIntId);
	}
	
	public Account getAccountByIdAndByClientId(String id, String client_id) throws DatabaseException, AccountNotFoundException {
		int parsedIntId = Integer.parseInt(id);
		int parsedIntClientId = Integer.parseInt(client_id);
		return clientRepo.getAccountByIdAndByClientId(parsedIntId, parsedIntClientId);
	}
	
	public List<Account> getAccountsWithClientId(String id) throws DatabaseException{
		int parsedIntID = Integer.parseInt(id);
		return clientRepo.getAccountByClientId(parsedIntID);
	}
	
	public List<Account> getAccountsForClientByIdBetween(String client_id, String lowerBalance, String higherBalance) throws DatabaseException, BrokenBalanceException {
		int parsedIntId = Integer.parseInt(client_id);
		int parsedLowerBalance = Integer.parseInt(lowerBalance);
		int parsedHigherBalance = Integer.parseInt(higherBalance);
		
		return clientRepo.getAccountsForClientByIdBetween(parsedIntId, parsedLowerBalance, parsedHigherBalance);
	}
	
	public Client addClient(Client client) throws DatabaseException {
		return clientRepo.addClient(client);
	}
	
	public Account addAccount(AccountDTO accountDTO) throws DatabaseException {
		return clientRepo.addAccount(accountDTO);
	}
	
	public void updateClientById(Client oldClient, ClientDTO newClientDTO) throws DatabaseException {
		
		clientRepo.updateClientById(oldClient, newClientDTO);
	}
	
	public void updateAccountByIdAndByClientId(Account account, AccountDTO accountDTO) throws DatabaseException {
		clientRepo.updateAccountByIdAndByClientId(account, accountDTO);
	}
	
	public void deleteClientById(String id) throws DatabaseException {
		int parsedId = Integer.parseInt(id);
		clientRepo.deleteClientById(parsedId);
	}
	
	public void deleteAccountByIdAndByClientId(String id, String client_id) throws DatabaseException {
		int parsedId = Integer.parseInt(id);
		int parsedClientId = Integer.parseInt(client_id);
		clientRepo.deleteAccountByIdAndByClientId(parsedId, parsedClientId);
	}
	
	public Client addClient(ClientDTO clientDTO) throws DatabaseException, AddClientException, BadParameterException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.clientRepo.setConnection(connection);
			connection.setAutoCommit(false);
			
			if(clientDTO.getFirst_name().trim().equals("")||clientDTO.getLast_name().trim().equals("")) {
				throw new AddClientException("Client first and/or last name cannot be blank!");
			}
			
			try {
				Client client = clientRepo.addClient(clientDTO);
				
				connection.commit();
				return client;
			}catch(NumberFormatException e ) {
				throw new BadParameterException("One of the parameters wasn't the correct type!");
			}
			
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}

	
}
