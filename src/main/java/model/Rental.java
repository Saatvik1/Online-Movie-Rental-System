package model;

public class Rental {
	
	
	/*
	 * This class is a representation of the rental table in the database
	 * Each instance variable has a corresponding getter and setter
	 */
	
	private int accountID;
	private int orderID;
	private int movieID;
	private int customerRepID;
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getMovieID() {
		return movieID;
	}
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	public int getCustomerRepID() {
		return customerRepID;
	}
	public void setCustomerRepID(int customerRepID) {
		this.customerRepID = customerRepID;
	}

	

}
