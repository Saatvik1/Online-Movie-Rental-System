package dao;

import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;

import java.sql.*;

public class EmployeeDao {
	String jdbcUrl = "jdbc:mysql://localhost:3306/cse305projectpt2";
    String dbusername = "root";
    String password = "CSE305";
    
	/*
	 * This class handles all the database operations related to the employee table
	 */
	
    public String addEmployee(Employee employee) {
        String result = "failure";
        // Database credentials and URL
        // SQL queries
        String checkLocationQuery = "SELECT COUNT(*) FROM Location WHERE ZipCode = ?";
        String insertLocationQuery = "INSERT INTO Location (ZipCode, City, State) VALUES (?, ?, ?)";
        String insertPersonQuery = "INSERT INTO Person (SSN, LastName, FirstName, Address, ZipCode, Telephone) VALUES (?, ?, ?, ?, ?, ?)";
        String insertEmployeeQuery = "INSERT INTO Employee (ID, SSN, StartDate, HourlyRate ,Email) VALUES (?, ?, ?, ?,?)";
        try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbusername, password)) {
            connection.setAutoCommit(false); // Start transaction

            // Check if location exists
            try (PreparedStatement checkLocationStmt = connection.prepareStatement(checkLocationQuery)) {
                checkLocationStmt.setInt(1, employee.getZipCode());
                ResultSet rs = checkLocationStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    // Location does not exist, insert it
                    try (PreparedStatement insertLocationStmt = connection.prepareStatement(insertLocationQuery)) {
                        insertLocationStmt.setInt(1, employee.getZipCode());
                        insertLocationStmt.setString(2, employee.getCity());
                        insertLocationStmt.setString(3, employee.getState());
                        insertLocationStmt.executeUpdate();
                    }
                }
            }

            // Insert into Person
            try (PreparedStatement psPerson = connection.prepareStatement(insertPersonQuery)) {
                psPerson.setString(1, employee.getEmployeeID());
                psPerson.setString(2, employee.getLastName());
                psPerson.setString(3, employee.getFirstName());
                psPerson.setString(4, employee.getAddress());
                psPerson.setInt(5, employee.getZipCode());
                psPerson.setString(6, employee.getTelephone());
                psPerson.executeUpdate();
            }

            // Insert into Employee
            
            try (PreparedStatement psEmployee = connection.prepareStatement(insertEmployeeQuery)) {
                psEmployee.setString(1, employee.getEmployeeID());
                psEmployee.setString(2, employee.getEmployeeID());
                psEmployee.setString(3, employee.getStartDate());
                psEmployee.setFloat(4, employee.getHourlyRate());
                psEmployee.setString(5, employee.getEmail());
                psEmployee.executeUpdate();
            }
            

            connection.commit(); // Commit transaction
            result = "success";
        } catch (SQLException e) {
           
           e.printStackTrace();
            // Handle exception
        }
        return result;
    }


    public String editEmployee(Employee employee) {
        String result = "failure";
        String updatePersonQuery = "UPDATE Person SET LastName = ?, FirstName = ?, Address = ?, ZipCode = ?, Telephone = ? WHERE SSN = ?";
        String updateEmployeeQuery = "UPDATE Employee SET StartDate = ?, HourlyRate = ?, Email = ? WHERE  SSN= ?";
        try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbusername, password);
             PreparedStatement psPerson = connection.prepareStatement(updatePersonQuery);
             PreparedStatement psEmployee = connection.prepareStatement(updateEmployeeQuery)) {

            // Update Person table
            psPerson.setString(1, employee.getLastName());
            psPerson.setString(2, employee.getFirstName());
            psPerson.setString(3, employee.getAddress());
            psPerson.setInt(4, employee.getZipCode());
            psPerson.setString(5, employee.getTelephone());
            psPerson.setString(6, employee.getEmployeeID()); // Assuming SSN is used as Employee ID
            int rowsAffectedPerson = psPerson.executeUpdate();

            // Update Employee table
            psEmployee.setString(1, employee.getStartDate());
            psEmployee.setFloat(2, employee.getHourlyRate());
            psEmployee.setString(3, employee.getEmail());
            psEmployee.setString(4, employee.getEmployeeID());
            int rowsAffectedEmployee = psEmployee.executeUpdate();

            if (rowsAffectedPerson > 0 || rowsAffectedEmployee > 0) {
                result = "success";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return result;
    }


    public String deleteEmployee(String employeeID) {
        String result = "failure";
        String deleteEmployeeQuery = "DELETE FROM Employee WHERE SSN = ?";
        String deletePersonQuery = "DELETE FROM Person WHERE SSN = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbusername, password)) {
                connection.setAutoCommit(false); // Start transaction

                // Delete from Employee table
                try (PreparedStatement preparedStatementEmployee = connection.prepareStatement(deleteEmployeeQuery)) {
                    preparedStatementEmployee.setString(1, employeeID);
                    preparedStatementEmployee.executeUpdate();
                }

                // Delete from Person table
                try (PreparedStatement preparedStatementPerson = connection.prepareStatement(deletePersonQuery)) {
                    preparedStatementPerson.setString(1, employeeID);
                    preparedStatementPerson.executeUpdate();
                }

                connection.commit(); // Commit transaction
                result = "success";
            } catch (SQLException e) {                              
                     e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

	
	public List<Employee> getEmployees() {
	    List<Employee> employees = new ArrayList<>();
	    String selectQuery = "SELECT e.ID,e.SSN , e.StartDate, e.HourlyRate, e.Email, p.FirstName, p.LastName, p.Address, p.ZipCode, p.Telephone, l.City, l.State " +
	                         "FROM Employee e " +
	                         "JOIN Person p ON e.SSN = p.SSN " +
	                         "JOIN Location l ON p.ZipCode = l.ZipCode";

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbusername, password);
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(selectQuery)) {

	            while (resultSet.next()) {
	                Employee employee = new Employee();
	                employee.setEmployeeID(resultSet.getString("SSN"));
	                employee.setStartDate(resultSet.getString("StartDate"));
	                employee.setHourlyRate(resultSet.getFloat("HourlyRate"));
	                employee.setEmail(resultSet.getString("Email")); // Fetching email
	                employee.setFirstName(resultSet.getString("FirstName"));
	                employee.setLastName(resultSet.getString("LastName"));
	                employee.setAddress(resultSet.getString("Address"));
	                employee.setZipCode(resultSet.getInt("ZipCode"));
	                employee.setTelephone(resultSet.getString("Telephone"));
	                employee.setCity(resultSet.getString("City"));
	                employee.setState(resultSet.getString("State"));
	                // Set other fields as needed
	                employees.add(employee);
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return employees;
	}


	public Employee getEmployee(String employeeID) {
	    Employee employee = null;
	    String selectQuery = "SELECT e.ID, e.StartDate, e.HourlyRate, p.FirstName, p.LastName, p.Address, p.ZipCode, p.Telephone, l.City, l.State " +
	                         "FROM Employee e " +
	                         "JOIN Person p ON e.SSN = p.SSN " +
	                         "JOIN Location l ON p.ZipCode = l.ZipCode " +
	                         "WHERE e.ID = ?";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbusername, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

	        preparedStatement.setString(1, employeeID);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            employee = new Employee();
	            employee.setEmployeeID(resultSet.getString("ID"));
	            employee.setStartDate(resultSet.getString("StartDate"));
	            employee.setHourlyRate(resultSet.getFloat("HourlyRate"));
	            employee.setFirstName(resultSet.getString("FirstName"));
	            employee.setLastName(resultSet.getString("LastName"));
	            employee.setAddress(resultSet.getString("Address"));
	            employee.setZipCode(resultSet.getInt("ZipCode"));
	            employee.setTelephone(resultSet.getString("Telephone"));
	            employee.setCity(resultSet.getString("City"));
	            employee.setState(resultSet.getString("State"));
	            // Set other fields as needed
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return employee;
	}

	
	public Employee getHighestRevenueEmployee() {
	    Employee employee = null;
	    String selectQuery = "SELECT e.SSN, e.Email, p.FirstName, p.LastName, COUNT(r.OrderId) AS SalesCount " +
	                         "FROM Employee e " +
	                         "JOIN Rental r ON e.ID = r.CustRepId " +
	                         "JOIN Person p ON e.SSN = p.SSN " +
	                         "GROUP BY e.ID " +
	                         "ORDER BY SalesCount DESC " +
	                         "LIMIT 1";

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbusername, password);
	             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                employee = new Employee();
	                employee.setEmployeeID(resultSet.getString("SSN"));
	                employee.setFirstName(resultSet.getString("FirstName"));
	                employee.setLastName(resultSet.getString("LastName"));
	                employee.setEmail(resultSet.getString("Email"));
	           
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle exception
	        }
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return employee;
	}



	public String getEmployeeID(String username) {
	    String employeeID = null;
	    String selectQuery = "SELECT ID FROM Employee WHERE Email = ?";
	    try {Class.forName("com.mysql.jdbc.Driver");}
        catch ( ClassNotFoundException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbusername, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

	        preparedStatement.setString(1, username);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            employeeID = resultSet.getString("ID");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return employeeID != null ? employeeID : "Employee Not Found";
	}
}