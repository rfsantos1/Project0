package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AccountDTO;
import com.revature.dto.ClientDTO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.BrokenBalanceException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.util.ConnectionUtil;

public class ClientRepo {
	
	private Connection connection;

	List<Client> listOfClients = new ArrayList<Client>();
	List<Account> listOfAccounts = new ArrayList<Account>();
	
	public ClientRepo() {
		super();
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public List<Client> getClients() {
		try(Connection connection = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM clients";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				int clientId = resultSet.getInt("id");
				String first_name = resultSet.getString("first_name");
				String last_name = resultSet.getString("last_name");
				int amountInAccount = resultSet.getInt("amount_in_account");
				
				Client client = new Client(clientId, first_name, last_name, amountInAccount);
				listOfClients.add(client);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfClients;
	}
	
	public List<Account> getAccounts() {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				int accountId = resultSet.getInt("id");
				String bank_name = resultSet.getString("bank_name");
				int balance = resultSet.getInt("balance");
				int clientId = resultSet.getInt("client_id");
				
				Account account = new Account(accountId, bank_name, balance, clientId);
				listOfAccounts.add(account);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfAccounts;
	}
	
	public Client getClientById(String id) throws DatabaseException, ClientNotFoundException {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parsedIntId = Integer.parseInt(id);
			String sql = "SELECT * FROM clients c WHERE c.id = ?";
			
			PreparedStatement prepStatement = connection.prepareStatement(sql);
			
			prepStatement.setInt(1, parsedIntId);
			
			ResultSet resultSet = prepStatement.executeQuery();
			
			if(resultSet.next()) {
				int clientId = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				int amountInAccount = resultSet.getInt("amount_in_account");
				
				return new Client(clientId, firstName, lastName, amountInAccount);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Something happened with the database. Exception message against: " + e.getMessage());
		}
		
		throw new ClientNotFoundException("Client with id " + id + " was not found.");
	}
	
	public Account getAccountByIdAndByClientId(int id, int client_id) throws DatabaseException, AccountNotFoundException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE id = ? AND client_id = ?";
			
			PreparedStatement prepStatement = connection.prepareStatement(sql);
			prepStatement.setInt(1, id);
			prepStatement.setInt(2, client_id);
			
			ResultSet resultSet = prepStatement.executeQuery();
			if(resultSet.next()) {
				int accountId = resultSet.getInt("id");
				String bank_name = resultSet.getString("bank_name");
				int balance = resultSet.getInt("balance");
				int clientId = resultSet.getInt("client_id");
				
				return new Account(accountId, bank_name, balance, clientId);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Something happened with the database. Exception message against: " + e.getMessage());
		}
		
		throw new AccountNotFoundException("Account with id: " + id + " and/or Clients with id: " + client_id + " were not found!");
	}
	
	public List<Account> getAccountByClientId(int id) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE client_id = ?";
			
			PreparedStatement prepStatement = connection.prepareStatement(sql);
			prepStatement.setInt(1, id);
			
			ResultSet resultSet = prepStatement.executeQuery();
			while(resultSet.next()) {
				int accountId = resultSet.getInt("id");
				String bank_name = resultSet.getString("bank_name");
				int balance = resultSet.getInt("balance");
				int clientId = resultSet.getInt("client_id");
				
				Account account = new Account(accountId, bank_name, balance, clientId);
				listOfAccounts.add(account);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Something happened with the database. Exception message against: " + e.getMessage());
		}
		return listOfAccounts;
	}
	
	public List<Account> getAccountsForClientByIdBetween(int id, int lowerBalance, int higherBalance) throws DatabaseException, BrokenBalanceException{
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance > ? AND balance < ?";
			
			if(lowerBalance > higherBalance) {
				throw new BrokenBalanceException("Lower Balance is higher than higherBalance. Please input the amounts in the correct balance.");
			}
			
			PreparedStatement prepStatement = connection.prepareStatement(sql);
			prepStatement.setInt(1, id);
			prepStatement.setInt(2, lowerBalance);
			prepStatement.setInt(3, higherBalance);
			
			ResultSet resultSet = prepStatement.executeQuery();
			while(resultSet.next()) {
				int accountId = resultSet.getInt("id");
				String bank_name = resultSet.getString("bank_name");
				int balance = resultSet.getInt("balance");
				int clientId = resultSet.getInt("client_id");
				
				Account account = new Account(accountId, bank_name, balance, clientId);
				listOfAccounts.add(account);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Something happened with the database. Exception message against: " + e.getMessage());
		}
		return listOfAccounts;
	}
	
	public Client getClientById(int i) throws DatabaseException, ClientNotFoundException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM clients c WHERE c.id = ?";
			
			PreparedStatement prepStatement = connection.prepareStatement(sql);
			prepStatement.setInt(1, i);
			
			ResultSet resultSet = prepStatement.executeQuery();
			
			if(resultSet.next()) {
				int clientId = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				int amountInAccount = resultSet.getInt("amount_in_account");
				
				return new Client(clientId, firstName, lastName, amountInAccount);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseException("Something happened with the database. Exception message against: " + e.getMessage());
		}
		
		throw new ClientNotFoundException("Client with id " + i + " was not found.");
	}
	
	public Client addClient(Client client) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO clients (first_name, last_name, amount_in_account) VALUES (?, ?, ?)";
			
			PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			prepStatement.setString(1, client.getFirst_name());
			prepStatement.setString(2, client.getLast_name());
			prepStatement.setInt(3, client.getAmountInAccount());
			
			int recordsModified = prepStatement.executeUpdate();
			if(recordsModified != 1) {
				throw new DatabaseException("Client was not able to be inserted into the database.");
			}
			
			ResultSet generatedKeys = prepStatement.getGeneratedKeys();
			if(generatedKeys.next()) {
				client.setId(generatedKeys.getInt(1));
			}else {
				throw new DatabaseException("No ID was able to be obtained when trying to insert a client. Client insertion failed.");
			}
			
			return client;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Something happened with the database. Exception message against: " + e.getMessage());
		}
	}
	
	public Account addAccount(AccountDTO accountDTO) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO accounts (bank_name, balance, client_id) VALUES (?, ?, ?)";
			
			PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			prepStatement.setString(1, accountDTO.getBank_name());
			prepStatement.setInt(2, accountDTO.getBalance());
			prepStatement.setInt(3, accountDTO.getClient_id());
			
			int recordsModified = prepStatement.executeUpdate();
			if(recordsModified != 1) {
				throw new DatabaseException("Account was not able to be inserted into the database.");
			}
			
			ResultSet generatedKeys = prepStatement.getGeneratedKeys();
			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				
				Account account = new Account(id, accountDTO.getBank_name(), accountDTO.getBalance(), accountDTO.getClient_id());
				return account;
			}else {
				throw new DatabaseException("No ID was able to be obtained when trying to insert a account. Account insertion failed.");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Something happened with the database. Exception message against: " + e.getMessage());
		}
	}
	
	public Client addClient(ClientDTO clientDTO) throws DatabaseException {
		try {
			String sql = "INSERT INTO clients (first_name, last_name, amount_in_account) VALUES(?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, clientDTO.getFirst_name());
			statement.setString(2, clientDTO.getLast_name());
			statement.setInt(3, clientDTO.getAmount_in_account());
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't add a client to the database");
			}
			
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				Client newClient = new Client(id, clientDTO.getFirst_name(), clientDTO.getLast_name(), clientDTO.getAmount_in_account());
				return newClient;
			} else {
				throw new DatabaseException("Client id was not generated...");
			}
			
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void updateClientById(Client client, ClientDTO clientDTO) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE clients SET first_name = ?, last_name = ?, amount_in_account = ? WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, clientDTO.getFirst_name());
			statement.setString(2, clientDTO.getLast_name());
			statement.setInt(3, clientDTO.getAmount_in_account());
			statement.setInt(4, client.getId());
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't update a client in the database");
			}
			
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void updateAccountByIdAndByClientId(Account account, AccountDTO accountDTO) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE accounts SET bank_name = ?, balance = ? WHERE id = ? AND client_id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, accountDTO.getBank_name());
			statement.setInt(2, accountDTO.getBalance());
			statement.setInt(3, account.getId());
			statement.setInt(4, account.getClient_id());
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't update a Account in the database");
			}
			
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void deleteClientById(int id) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			deleteAccountsByClientId(id);
			
			String sql = "DELETE FROM clients WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't delete a client in the database.");
			}
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void deleteAccountById(int id) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM accounts WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't delete a account in the database.");
			}
					
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void deleteAccountByIdAndByClientId(int id, int client_id) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM accounts WHERE id = ? AND client_id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			statement.setInt(2, client_id);
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't delete a account in the database.");
			}
					
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void deleteAccountsByClientId(int foreign_id) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM accounts WHERE client_id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, foreign_id);
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't delete a account in the database.");
			}
					
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void deleteAccountByIdBelongingToClientById(int foreign_id, int id) throws DatabaseException {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM accounts WHERE id = ? AND client_id = ? ";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			statement.setInt(2, foreign_id);
			
			int recordsAdded = statement.executeUpdate();
			if(recordsAdded != 1) {
				throw new DatabaseException("Couldn't delete a account in the database.");
			}
					
		}catch(SQLException e) {
			throw new DatabaseException("Something went wrong with the database. "
					+ "Exception message: " + e.getMessage());
		}
	}

}
