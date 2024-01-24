package model;

public class Order {
	
	/*
	 * This class is a representation of the order table in the database
	 * Each instance variable has a corresponding getter and setter
	 */
	
	
	private int orderID;
	private String dateTime;
	private String returnDate;
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

}
