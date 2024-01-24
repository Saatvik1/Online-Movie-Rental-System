package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Login;


public class LoginDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */
	
	
	public Login login(String username, String password) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */
		
		
		String URL = "jdbc:mysql://localhost:3306/cse305projectpt2";
		String Id = "root";
		String Passwd = "CSE305";
		String query = "SELECT * FROM Login L WHERE L.Email = ? AND L.Password = ?;";
		
		Connection con;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, Id, Passwd);
			Statement stat = con.createStatement();
			PreparedStatement ps= con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet result = ps.executeQuery();
			
			
			while(result.next()) {
				Login login = new Login();
				login.setRole(result.getString("Role"));
				login.setPassword(password);
				login.setUsername(username);
				
				return login;
			}
			
			
			
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("what da fuq");
			e.printStackTrace();
		}

		
		/*Sample data begins*/
				
		
		//login.setRole("customerRepresentative");
		//login.setRole("manager");
		//login.setRole("customer");
		return null;
		/*Sample data ends*/
		
	}
	
	public String addUser(Login login) {
		/*
		 * Query to insert a new record for user login must be implemented
		 * login, which is the "Login" Class object containing username and password for the new user, is given as method parameter
		 * The username and password from login can get accessed using getter methods in the "Login" model
		 * e.g. getUsername() method will return the username encapsulated in login object
		 * Return "success" on successful insertion of a new user
		 * Return "failure" for an unsuccessful database operation
		 */
		
		String URL = "jdbc:mysql://localhost:3306/cse305projectpt2";
		String Id = "root";
		String Passwd = "CSE305";
		String query = "INSERT INTO Login (Email, Password, Role) VALUES (?, ?, ?)";
		
		
		Connection con;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, Id, Passwd);
			Statement stat = con.createStatement();
			PreparedStatement ps= con.prepareStatement(query);
			ps.setString(1, login.getUsername());
			ps.setString(2, login.getPassword());
			ps.setString(3, login.getRole());
			
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
