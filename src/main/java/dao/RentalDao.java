package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Rental;

public class RentalDao {
	
	public List<Rental> getOrderHisroty(String customerID) {
		
		List<Rental> rentals = new ArrayList<Rental>();
		
		String URL = "jdbc:mysql://localhost:3306/cse305projectpt2";
		String Id = "root";
		String Passwd = "CSE305";
//		String query = "SELECT DISTINCT `order`.Id as OrderID, `rental`.MovieID as MovieId, `rental`.CustRepId as CustomerRepresentitiveID "
//				+ "FROM `rental` JOIN `movie` ON `rental`.MovieId = `movie`.Id "
//				+ "JOIN `order` ON `order`.Id = `rental`.OrderId "
//				+ "WHERE `rental`.AccountId = ?;";
		
		String query = "SELECT * FROM Rental R, Account A WHERE A.Customer = ? AND A.Id = R.AccountId;";
		
		Connection con;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, Id, Passwd);
			System.out.print("Connection successful AccountDao");
			Statement stat = con.createStatement();
			System.out.print("Statement creation successful AccountDao");
			PreparedStatement ps= con.prepareStatement(query);
			ps.setString(1, customerID);
			
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				Rental rental = new Rental();
				
				rental.setOrderID(result.getInt("OrderID"));
				rental.setMovieID(result.getInt("MovieID"));
				rental.setCustomerRepID(result.getInt("CustRepId"));
			
				rentals.add(rental);
			}
			
			
			
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("what da fuq");
			e.printStackTrace();
		}
			
		/*Sample data begins*/
//		for (int i = 0; i < 4; i++) {
//			Rental rental = new Rental();
//			
//			rental.setOrderID(1);
//			rental.setMovieID(1);
//			rental.setCustomerRepID(1);
//		
//			rentals.add(rental);
//			
//			
//		}
		/*Sample data ends*/
						
		return rentals;
		
	}

}
