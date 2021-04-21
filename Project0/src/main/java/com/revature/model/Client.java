package com.revature.model;

public class Client {
	private int id;
	private String first_name;
	private String last_name;
	private int amount_in_account;
	
	public Client() {
		super();
	}
	
	public Client(int id, String first_name, String last_name, int amountInAccount) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.amount_in_account = amountInAccount;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount_in_account;
		result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
		result = prime * result + id;
		result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
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
		Client other = (Client) obj;
		if (amount_in_account != other.amount_in_account)
			return false;
		if (first_name == null) {
			if (other.first_name != null)
				return false;
		} else if (!first_name.equals(other.first_name))
			return false;
		if (id != other.id)
			return false;
		if (last_name == null) {
			if (other.last_name != null)
				return false;
		} else if (!last_name.equals(other.last_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", amount_in_account="
				+ amount_in_account + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getAmountInAccount() {
		return amount_in_account;
	}

	public void setAmountInAccount(int amountInAccount) {
		this.amount_in_account = amountInAccount;
	}

}
