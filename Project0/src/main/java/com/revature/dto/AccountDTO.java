package com.revature.dto;

public class AccountDTO {
	private int id;
	private String bank_name;
	private int balance;
	private int client_id;
	
	public AccountDTO() {
		super();
	}
	
	public AccountDTO(int id, String bank_name, int balance, int client_id) {
		this.id = id;
		this.bank_name = bank_name;
		this.balance = balance;
		this.client_id = client_id;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + balance;
		result = prime * result + ((bank_name == null) ? 0 : bank_name.hashCode());
		result = prime * result + client_id;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountDTO other = (AccountDTO) obj;
		if (balance != other.balance)
			return false;
		if (bank_name == null) {
			if (other.bank_name != null)
				return false;
		} else if (!bank_name.equals(other.bank_name))
			return false;
		if (client_id != other.client_id)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", bank_name=" + bank_name + ", balance=" + balance + ", client_id=" + client_id
				+ "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
}
