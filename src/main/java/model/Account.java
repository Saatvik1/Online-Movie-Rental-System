package model;

public class Account {
	private int accountID;
	private String acctCreateDate;
	private String accountType;
	private String customerID;
	
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAcctCreateDate() {
		return acctCreateDate;
	}
	public void setAcctCreateDate(String acctCreateDate) {
		this.acctCreateDate = acctCreateDate;
	}

}
