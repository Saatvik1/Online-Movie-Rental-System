package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.Employee;
import model.Movie;
import model.Account;

public class AccountDao {
	
	public int getSalesReport(Account account) {
			
			/*
			 * The students code to fetch data from the database will be written here
			 * Query to get sales report for a particular month must be implemented
			 * account, which has details about the month and year for which the sales report is to be generated, is given as method parameter
			 * The month and year are in the format "month-year", e.g. "10-2018" and stored in the dateOpened attribute of account object
			 * The month and year can be accessed by getter method, i.e., account.getAcctCreateDate()
			 */
		
			String URL = "jdbc:mysql://localhost:3306/cse305projectpt2";
			String Id = "root";
			String Passwd = "CSE305";
			String query = "SELECT"
		            + " SUM("
		            + " CASE"
		            + " WHEN A.Type = 'limited' THEN 10"
		            + " WHEN A.Type = 'unlimited-1' THEN 15"
		            + " WHEN A.Type = 'unlimited-2' THEN 20"
		            + " WHEN A.Type = 'unlimited-3' THEN 25"
		            + " ELSE 0 "
		            + " END"
		            + " ) AS TotalIncome"
		            + " FROM"
		            + " Account A"
		            + " WHERE"
		            + " YEAR(A.DateOpened)*12 + MONTH(A.DateOpened) <= ?*12 + ?";
			
			String dateOpenedMonth = account.getAcctCreateDate().substring(0,1);
			String dateOpenedYear = account.getAcctCreateDate().substring(2,6);
			
			System.out.println(dateOpenedMonth + " " + dateOpenedYear);
			
			Connection con;
			int income = 0;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(URL, Id, Passwd);
				System.out.print("Connection successful AccountDao");
				Statement stat = con.createStatement();
				System.out.print("Statement creation successful AccountDao");
				PreparedStatement ps= con.prepareStatement(query);
				
				ps.setString(2, dateOpenedMonth);
				ps.setString(1, dateOpenedYear);
				
				ResultSet result = ps.executeQuery();
				result.next();
				
				System.out.println(result);
				System.out.println(result.getInt("TotalIncome") + " this is the result");
				income = result.getInt("TotalIncome");
				
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.print("what da fuq");
				e.printStackTrace();
			}

			
	
			
					
			/*Sample data begins*/
			//income = 100;
			/*Sample data ends*/
			
	
			return income;
			
		}
	
	public String setAccount(String customerID, String accountType) {

		String URL = "jdbc:mysql://localhost:3306/cse305projectpt2";
		String Id = "root";
		String Passwd = "CSE305";
//		String query = "SELECT A.Customer FROM Account A, Customer C WHERE C.Id = A.CustomerId AND C.Id = ?";
//		String secondQuery = "UPDATE Account SET Type = ? WHERE Id = ?;";
		String query = "UPDATE Account SET Type = ? WHERE Customer = ?;";
		Connection con;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, Id, Passwd);
			Statement stat = con.createStatement();
			PreparedStatement ps= con.prepareStatement(query);
			ps.setString(2, customerID);
			ps.setString(1, accountType);
//			
//			ResultSet result = ps.executeQuery();
			
//			while(result.next()) {
//				ps = con.prepareStatement(secondQuery);
//				ps.setString(1, result.getInt("Customer")t);
//			}
			
			ps.executeUpdate();
			
			return "success";			
			
			
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("what da fuq");
			e.printStackTrace();
		}
		
		/*Sample data begins*/
		return "failure";
		/*Sample data ends*/
	}
		
	

}
