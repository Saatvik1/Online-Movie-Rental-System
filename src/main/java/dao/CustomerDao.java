package dao;

import java.sql.*;

import java.util.ArrayList;
import java.sql.DriverManager;
import java.util.List;

import model.Customer;



public class CustomerDao {
	String jdbcUrl = "jdbc:mysql://localhost:3306/cse305projectpt2";
    String username = "root";
    String password = "CSE305";
	

	/*
	 * This class handles all the database operations related to the customer table
	 */
	
	/**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        String selectQuery = "SELECT p.SSN, p.LastName, p.FirstName, p.Address, p.ZipCode, p.Telephone, l.City, l.State, c.Email, c.Rating, c.CreditCardNumber " +
                             "FROM Person p " +
                             "JOIN Customer c ON p.SSN = c.Id " +
                             "LEFT JOIN Location l ON p.ZipCode = l.ZipCode";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle exception
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getString("SSN"));
                customer.setLastName(resultSet.getString("LastName"));
                customer.setFirstName(resultSet.getString("FirstName"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setZipCode(resultSet.getInt("ZipCode"));
                customer.setTelephone(resultSet.getString("Telephone"));
                customer.setCity(resultSet.getString("City"));
                customer.setState(resultSet.getString("State"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setRating(resultSet.getInt("Rating"));
                customer.setCreditCard(resultSet.getString("CreditCardNumber"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }





    public Customer getHighestRevenueCustomer() {
        Customer customer = null;
        String selectQuery = "SELECT c.Id,c.Email, p.FirstName, p.LastName, COUNT(r.OrderId) AS RentalCount " +
                             "FROM Customer c " +
                             "JOIN Person p ON c.Id = p.SSN " +
                             "JOIN Account a ON c.Id = a.Customer " +
                             "JOIN Rental r ON a.Id = r.AccountId " +
                             "GROUP BY c.Id " +
                             "ORDER BY RentalCount DESC " +
                             "LIMIT 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle exception
        }
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customer = new Customer();
                customer.setCustomerID(resultSet.getString("Id"));
                customer.setFirstName(resultSet.getString("FirstName"));
                customer.setLastName(resultSet.getString("LastName"));
                customer.setEmail(resultSet.getString("Email"));
                // Add other fields as necessary
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return customer;
    }



	public List<Customer> getCustomerMailingList() {
	    List<Customer> customers = new ArrayList<>();
	   
	    String selectQuery = "SELECT p.SSN, p.FirstName, p.LastName, p.Address, p.ZipCode, l.City, l.State, p.Telephone, c.Email " +
	                         "FROM Person p " +
	                         "JOIN Customer c ON p.SSN = c.Id " +
	                         "LEFT JOIN Location l ON p.ZipCode = l.ZipCode";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(selectQuery)) {

	        while (resultSet.next()) {
	            Customer customer = new Customer();
	            customer.setCustomerID(resultSet.getString("SSN"));
	            customer.setFirstName(resultSet.getString("FirstName"));
	            customer.setLastName(resultSet.getString("LastName"));
	            customer.setAddress(resultSet.getString("Address"));
	            customer.setZipCode(resultSet.getInt("ZipCode"));
	            customer.setCity(resultSet.getString("City"));
	            customer.setState(resultSet.getString("State"));
	            customer.setEmail(resultSet.getString("Email"));
	            // Set other fields as needed
	            customers.add(customer);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return customers;
	}


	public Customer getCustomer(String customerID) {
	    Customer customer = null;
	    String selectQuery = "SELECT p.SSN, p.LastName, p.FirstName, p.Address, p.ZipCode, p.Telephone, l.City, l.State, c.Email, c.Rating, c.CreditCardNumber " +
	                         "FROM Person p " +
	                         "JOIN Customer c ON p.SSN = c.Id " +
	                         "LEFT JOIN Location l ON p.ZipCode = l.ZipCode " +
	                         "WHERE c.Id = ?";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

	        preparedStatement.setString(1, customerID);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            customer = new Customer();
	            customer.setCustomerID(resultSet.getString("SSN"));
	            customer.setLastName(resultSet.getString("LastName"));
	            customer.setFirstName(resultSet.getString("FirstName"));
	            customer.setAddress(resultSet.getString("Address"));
	            customer.setZipCode(resultSet.getInt("ZipCode"));
	            customer.setTelephone(resultSet.getString("Telephone"));
	            customer.setCity(resultSet.getString("City"));
	            customer.setState(resultSet.getString("State"));
	            customer.setEmail(resultSet.getString("Email"));
	            customer.setRating(resultSet.getInt("Rating"));
	            customer.setCreditCard(resultSet.getString("CreditCardNumber"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return customer;
	}

	
	public String deleteCustomer(String customerID) {
	    String result = "failure";

	    String deleteCustomerQuery = "DELETE FROM Customer WHERE Id = ?";
	    String deletePersonQuery = "DELETE FROM Person WHERE SSN = ?";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	         PreparedStatement psCustomer = connection.prepareStatement(deleteCustomerQuery);
	         PreparedStatement psPerson = connection.prepareStatement(deletePersonQuery)) {

	        connection.setAutoCommit(false);

	        // Delete from Customer table
	        psCustomer.setString(1, customerID);
	        psCustomer.executeUpdate();

	        // Delete from Person table
	        psPerson.setString(1, customerID);
	        psPerson.executeUpdate();

	        connection.commit();
	        result = "success";
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return result;
	}



	public String getCustomerID(String email) {
	    String customerID = null;
	    String selectQuery = "SELECT Id FROM Customer WHERE Email = ?";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

	        preparedStatement.setString(1, email);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            customerID = resultSet.getString("Id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return customerID != null ? customerID : "Customer Not Found";
	}



	public List<Customer> getSellers() {
		
		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		List<Customer> customers = new ArrayList<Customer>();
		
		/*Sample data begins*/
		for (int i = 0; i < 10; i++) {
			Customer customer = new Customer();
			customer.setCustomerID("111-11-1111");
			customer.setAddress("123 Success Street");
			customer.setLastName("Lu");
			customer.setFirstName("Shiyong");
			customer.setCity("Stony Brook");
			customer.setState("NY");
			customer.setEmail("shiyong@cs.sunysb.edu");
			customer.setZipCode(11790);
			customers.add(customer);			
		}
		/*Sample data ends*/
		
		return customers;

	}


	public String addCustomer(Customer customer) {
	    String result = "failure";
	    String insertPersonQuery = "INSERT INTO Person (SSN, LastName, FirstName, Address, ZipCode, Telephone) VALUES (?, ?, ?, ?, ?, ?)";
	    String insertCustomerQuery = "INSERT INTO Customer (Id, Email, Rating, CreditCardNumber) VALUES (?, ?, ?, ?)";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	         PreparedStatement psPerson = connection.prepareStatement(insertPersonQuery);
	         PreparedStatement psCustomer = connection.prepareStatement(insertCustomerQuery)) {

	        connection.setAutoCommit(false);

	        // Insert into Person
	        psPerson.setString(1, customer.getCustomerID());
	        psPerson.setString(2, customer.getLastName());
	        psPerson.setString(3, customer.getFirstName());
	        psPerson.setString(4, customer.getAddress());
	        psPerson.setInt(5, customer.getZipCode());
	        psPerson.setString(6, customer.getTelephone());
	        psPerson.executeUpdate();

	        // Insert into Customer
	        psCustomer.setString(1, customer.getCustomerID());
	        psCustomer.setString(2, customer.getEmail());
	        psCustomer.setInt(3, customer.getRating());
	        psCustomer.setString(4, customer.getCreditCard());
	        psCustomer.executeUpdate();

	        connection.commit();
	        result = "success";
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	    } 
	    
	    return result;
	}



	public String editCustomer(Customer customer) {
	    String result = "failure";
	    String updatePersonQuery = "UPDATE Person SET LastName = ?, FirstName = ?, Address = ?, ZipCode = ?, Telephone = ? WHERE SSN = ?";
	    String updateCustomerQuery = "UPDATE Customer SET Email = ?, Rating = ?, CreditCardNumber = ? WHERE Id = ?";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	         PreparedStatement psPerson = connection.prepareStatement(updatePersonQuery);
	         PreparedStatement psCustomer = connection.prepareStatement(updateCustomerQuery)) {

	        connection.setAutoCommit(false);

	        // Update Person table
	        psPerson.setString(1, customer.getLastName());
	        psPerson.setString(2, customer.getFirstName());
	        psPerson.setString(3, customer.getAddress());
	        psPerson.setInt(4, customer.getZipCode());
	        psPerson.setString(5, customer.getTelephone());
	        psPerson.setString(6, customer.getCustomerID());
	        psPerson.executeUpdate();

	        // Update Customer table
	        psCustomer.setString(1, customer.getEmail());
	        psCustomer.setInt(2, customer.getRating());
	        psCustomer.setString(3, customer.getCreditCard());
	        psCustomer.setString(4, customer.getCustomerID());
	        psCustomer.executeUpdate();

	        connection.commit();
	        result = "success";
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return result;
	}


}