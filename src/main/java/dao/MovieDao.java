package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.Employee;
import model.Movie;

public class MovieDao {
	/* Student added method */
	public ResultSet performMySQLQuery(String query) {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cse305projectpt2", "root", "CSE305");
			st = con.createStatement();
			rs = st.executeQuery(query);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return rs;
	}
	
	public int performMySQLUpdate(String query) {
		Connection con = null;
		Statement st = null;
		int rows = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cse305projectpt2", "root", "CSE305");
			st = con.createStatement();
			rows = st.executeUpdate(query);
		} catch (SQLIntegrityConstraintViolationException se){
			System.out.println(se);
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
		
		return rows;
	}
	
	public List<Movie> getMovies() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of all the movies has to be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 */

		List<Movie> movies = new ArrayList<Movie>();
				
		String query = "SELECT * FROM Movie;";
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins
		for (int i = 0; i < 10; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movie.setDistFee(10000);
			movie.setNumCopies(3);
			movie.setRating(5);
			movies.add(movie);
		}
		Sample data ends*/
		
		return movies;

	}
	
	public Movie getMovie(String movieID) {

		/*
		 * The students code to fetch data from the database based on "movieID" will be written here
		 * movieID, which is the Movie's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Movie" class object
		 */

		Movie movie = new Movie();
		
		String query = String.format("SELECT * FROM Movie "
				+ "WHERE Movie.Id = %d", Integer.parseInt(movieID));
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins
		movie.setMovieID(1);
		movie.setMovieName("The Godfather");
		movie.setMovieType("Drama");
		movie.setDistFee(10000);
		movie.setNumCopies(3);
		movie.setRating(5);
		Sample data ends*/
		
		return movie;
	}
	
	public String addMovie(Movie movie) {

		/*
		 * All the values of the add movie form are encapsulated in the movie object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. movieName can be accessed by movie.getMovieName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the movie details and return "success" or "failure" based on result of the database insertion.
		 */
		
		// errrm. very bad.
		
		int rows = 0;
		String query = String.format("INSERT INTO Movie (Name, Type, Rating, DistrFee, NumCopies) "
				+ "VALUES ('%s','%s','%d','%d','%d');",
				movie.getMovieName(),
				movie.getMovieType(),
				movie.getRating(),
				movie.getDistFee(),
				movie.getNumCopies()
				);
		rows = performMySQLUpdate(query);
		String result = rows == 1 ? "success" : "fail";
		/*Sample data begins*/
		return result;
		/*Sample data ends*/

	}
	
	public String editMovie(Movie movie) {
		/*
		 * All the values of the edit movie form are encapsulated in the movie object.
		 * These can be accessed by getter methods (see Movie class in model package).
		 * e.g. movieName can be accessed by movie.getMovieName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		String query = String.format("UPDATE Movie "
				+ "SET Name = '%s', "
				+ "Type = '%s', "
				+ "Rating = %d, "
				+ "DistrFee = %d, "
				+ "NumCopies = %d "
				+ "WHERE Id = %d;",
				movie.getMovieName(),
				movie.getMovieType(),
				movie.getRating(),
				movie.getDistFee(),
				movie.getNumCopies(),
				movie.getMovieID()
				);
		int rows = performMySQLUpdate(query);
		String result = rows == 1 ? "success" : "fail";
		/*Sample data begins*/
		return result;
		/*Sample data ends*/

	}

	public String deleteMovie(String movieID) {
		/*
		 * movieID, which is the Movie's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		
		int movieId_INT = Integer.parseInt(movieID);
		String query_one = String.format("DELETE FROM AppearedIn "
				+ "Where MovieId = %d; ",
				movieId_INT
				);
		String query_two = String.format("DELETE FROM Rental "
				+ "WHERE MovieId = %d; ",
				movieId_INT
				);
		String query_three = String.format("DELETE FROM Movie "
				+ "WHERE Id = %d;",
				movieId_INT
				);
		performMySQLUpdate(query_one);
		performMySQLUpdate(query_two);
		int rows = performMySQLUpdate(query_three);
		String result = rows == 1 ? "success" : "fail";
		/*Sample data begins*/
		return result;

	}
	
	
	public List<Movie> getBestsellerMovies() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of the bestseller movies has to be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 */

		List<Movie> movies = new ArrayList<Movie>();
		String query = "SELECT * FROM Movie WHERE ID IN (SELECT DISTINCT Movie.Id "
				+ "FROM Rental, Movie "
				+ "WHERE Movie.Id = Rental.MovieId "
				+ "GROUP BY Movie.Id "
				+ "ORDER BY COUNT(Rental.OrderId) desc) "
				+ "LIMIT 10;";
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(rs.getInt("Id"));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
			
		
		/*Sample data begins
		for (int i = 0; i < 5; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
		}
		Sample data ends*/
		
		return movies;

	}

	public List<Movie> getSummaryListing(String searchKeyword) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of summary listing of revenue generated by a particular movie or movie type must be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 * Store the revenue generated by an movie in the soldPrice attribute, using setSoldPrice method of each "movie" object
		 */
		

		List<Movie> movies = new ArrayList<Movie>();
		
		String query = "SELECT * "
				+ "FROM Movie M "
				+ "INNER JOIN Rental R ON R.MovieId = M.Id "
				+ "WHERE M.Name LIKE %" + searchKeyword + "%";
		
		System.out.println(query);
		
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					
					// THIS DOESN'T EXIST OMGGOMGMOGOMGOMG
					//movie.setSoldPrice((int) Double.parseDouble(rs.getString("DistrFee")));
					
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
				
		/*Sample data begins
		for (int i = 0; i < 6; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movie.setDistFee(10000);
			movie.setNumCopies(3);
			movie.setRating(5);
			movies.add(movie);
		}
		Sample data ends*/
		
		return movies;

	}
	
	

	
	//testsed
	public List<Movie> getMovieSuggestions(String customerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch movie suggestions for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom the movie suggestions are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();
		
		customerID = customerID.replace("-", "");
		
		String query = String.format("SELECT * "
				+ "FROM Movie M1 "
				+ "WHERE M1.Id NOT IN ( "
				+ "    SELECT M2.Id "
				+ "    FROM Movie M2 "
				+ "    INNER JOIN Rental R ON R.MovieId = M2.Id "
				+ "    INNER JOIN Account A ON A.Id = R.AccountId "
				+ "    INNER JOIN Customer C ON C.Id = A.Customer "
				+ "	   WHERE C.Id = %d"
				+ ");", Integer.parseInt(customerID));
		
		/*
		 *  \n"
				+ "AND M1.Type IN (\n"
				+ "    SELECT M3.Type\n"
				+ "    FROM Movie M3\n"
				+ "    INNER JOIN Rental R ON R.MovieId = M3.Id \n"
				+ "    INNER JOIN Account A ON A.Id = R.AccountId \n"
				+ "    INNER JOIN Customer C ON C.Id = A.Customer\n"
				+ "    WHERE C.Id = %d"
				+ ");"
		 */
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins*/
		/*
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
		}
		*/
		/*Sample data ends*/
		
		return movies;

	}
	
	// testesd
	public List<Movie> getCurrentMovies(String customerID){
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch currently hold movie for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom currently hold movie are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();
		
		// Why does it have dashes
		
		customerID = customerID.replace("-", "");
		
		String query = String.format("SELECT * "
				+ "FROM Movie M "
				+ "INNER JOIN Rental R ON R.MovieId = M.Id "
				+ "INNER JOIN Account A ON A.Id = R.AccountId "
				+ "INNER JOIN Customer C ON C.Id = A.Customer "
				+ "WHERE C.Id = %d;", Integer.parseInt(customerID));
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(rs.getInt("Id"));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(rs.getInt("Rating"));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		
		/*Sample data begins
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movies.add(movie);
		}
		Sample data ends*/
		
		return movies;
		
		
		
	}
	
	/* TESTTESTTEST!!! */
	public List<Movie> getQueueOfMovies(String customerID){
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch movie queue for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom movie queue are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();
		
		customerID = customerID.replace("-", "");
		
		String query = String.format("SELECT * "
				+ "FROM Movie M "
				+ "INNER JOIN MovieQ Q ON Q.MovieId = M.Id "
				+ "INNER JOIN Account A ON A.Id = Q.AccountId "
				+ "INNER JOIN Customer C ON C.Id = A.Customer "
				+ "WHERE C.id = %d;", Integer.parseInt(customerID));
		System.out.println(query);
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins
		 * 
		 * 
		 * 
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
		}
		Sample data ends*/
		
		return movies;
		
		
		
	}
	
	

//	public List getMoviesBySeller(String sellerID) {
//		
//		/*
//		 * The students code to fetch data from the database will be written here
//		 * Query to fetch movies sold by a given seller, indicated by sellerID, must be implemented
//		 * sellerID, which is the Sellers's ID who's movies are fetched, is given as method parameter
//		 * The bid and order details of each of the movies should also be fetched
//		 * The bid details must have the highest current bid for the movie
//		 * The order details must have the details about the order in which the movie is sold
//		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
//		 * Each bid record is required to be encapsulated as a "Bid" class object and added to the "bids" List
//		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
//		 * The movies, bids and orders Lists have to be added to the "output" List and returned
//		 */
//
//		List output = new ArrayList();
//		List<Movie> movies = new ArrayList<Movie>();
//		List<Bid> bids = new ArrayList<Bid>();
//		List<Order> orders = new ArrayList<Order>();
//		
//		/*Sample data begins*/
//		for (int i = 0; i < 4; i++) {
//			Movie movie = new Movie();
//			movie.setMovieID(123);
//			movie.setDescription("sample description");
//			movie.setType("BOOK");
//			movie.setName("Sample Book");
//			movies.add(movie);
//			
//			Bid bid = new Bid();
//			bid.setCustomerID("123-12-1234");
//			bid.setBidPrice(120);
//			bids.add(bid);
//			
//			Order order = new Order();
//			order.setMinimumBid(100);
//			order.setBidIncrement(10);
//			order.setOrderID(123);
//			orders.add(order);
//		}
//		/*Sample data ends*/
//		
//		output.add(movies);
//		output.add(bids);
//		output.add(orders);
//		
//		return output;
//	}

    //
	public List<Movie> getMovieTypes() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 * A query to fetch the unique movie types has to be implemented
		 * Each movie type is to be added to the "movie" object using setType method
		 */
		
		List<Movie> movies = new ArrayList<Movie>();
		
		
		String query = "SELECT DISTINCT Type FROM Movie "
				+ "GROUP BY Type";
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieType(rs.getString("Type"));
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins
		for (int i = 0; i < 6; i++) {
			Movie movie = new Movie();
			movie.setMovieType("Joe");
			movies.add(movie);
		}
		Sample data ends*/
		
		return movies;
	}

	// TEST
	public List getMoviesByName(String movieName) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The movieName, which is the movie's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieName in their name has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();
		
		String query = String.format("SELECT * FROM Movie "
				+ "WHERE Name = '%s';", movieName);
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
			
		}
		Sample data ends*/
		

		
		return movies;
	}
	
	public List getMoviesByActor(String actorName) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The movieName, which is the movie's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieName in their name has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();
		
		String query = String.format("SELECT Movie.* FROM Actor, Movie, AppearedIn "
				+ "WHERE Actor.Id = AppearedIn.ActorId AND Actor.Name = '%s' AND AppearedIn.MovieId = Movie.Id", actorName);
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					movies.add(movie);
					//movie.setMovieType(rs.getString("Type"));
					//movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					//movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					//movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
			
		}
		Sample data ends*/
		

		
		return movies;
	}
	
	// Test
	public List getMoviesByType(String movieType) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The movieType, which is the movie's type on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieType as their type has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();
		
		String query = String.format("SELECT * FROM Movie "
				+ "WHERE Type = '%s';", movieType);
		System.out.println(query);
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					
					movies.add(movie);
					
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
				
		/*Sample data begins
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
			
		}
		Sample data ends*/
		

		
		return movies;
	}
	
	public List getMovieRentalsByName(String movieName) {
		
		List<Movie> movies = new ArrayList<Movie>();
		
		String query = String.format("SELECT M.* "
				+ "FROM Rental R "
				+ "INNER JOIN `Order` O ON R.OrderId = O.Id "
				+ "INNER JOIN Movie M ON R.MovieId = M.Id "
				+ "INNER JOIN Account A ON A.Id = R.AccountId "
				+ "INNER JOIN Customer C ON A.Customer = C.Id "
				+ "INNER JOIN Person P ON C.Id = P.SSN "
				+ "WHERE M.Name = '%s';", movieName);
		System.out.println(query);
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		//movies = new ArrayList<Movie>();
		/*Sample data begins
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
			
		}
		Sample data ends*/
		

		
		return movies;
	}
	
	public List getMovieRentalsByCustomer(String customerName) {
		List<Movie> movies = new ArrayList<Movie>();
		
		// Lewis
		
		String query = String.format("SELECT M.* "
				+ "FROM Rental R "
				+ "INNER JOIN `Order` O ON R.OrderId = O.Id "
				+ "INNER JOIN Movie M ON R.MovieId = M.Id "
				+ "INNER JOIN Account A ON A.Id = R.AccountId "
				+ "INNER JOIN Customer C ON A.Customer = C.Id "
				+ "INNER JOIN Person P ON C.Id = P.SSN "
				+ "WHERE P.FirstName = '%s' OR P.LastName = '%s' OR CONCAT(P.FirstName, ' ', P.LastName) = '%s' OR CONCAT(P.LastName, ' ', P.FirstName) = '%s';", customerName,customerName,customerName,customerName);
		System.out.println(query);
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/*Sample data begins
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
			
		}
		Sample data ends*/
		

		
		return movies;
	}
	
	public List getMovieRentalsByType(String movieType) {
		
	

		List<Movie> movies = new ArrayList<Movie>();
		
		String query = String.format("SELECT M.* "
				+ "FROM Rental R "
				+ "INNER JOIN `Order` O ON R.OrderId = O.Id "
				+ "INNER JOIN Movie M ON R.MovieId = M.Id "
				+ "INNER JOIN Account A ON A.Id = R.AccountId "
				+ "INNER JOIN Customer C ON A.Customer = C.Id "
				+ "INNER JOIN Person P ON C.Id = P.SSN "
				+ "WHERE M.Type = '%s'", movieType);
		System.out.println(query);
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
				
		/*Sample data begins
		for (int i = 0; i < 4; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movies.add(movie);
			
		}
		Sample data ends*/
		

		
		return movies;
	}
	
	// Test
	public List<Movie> getBestsellersForCustomer(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList.
		 * Query to get the Best-seller list of movies for a particular customer, indicated by the customerID, has to be implemented
		 * The customerID, which is the customer's ID for whom the Best-seller movies have to be fetched, is given as method parameter
		 */

		List<Movie> movies = new ArrayList<Movie>();
		
		customerID = customerID.replace("-", "");
		
		String query = String.format("SELECT * "
				+ "FROM Movie "
				+ "WHERE ID IN "
				+ "(SELECT M.Id "
				+ "FROM Movie M "
				+ "INNER JOIN Rental R ON R.MovieId = M.Id "
				+ "INNER JOIN Account A ON A.Id = R.AccountId "
				+ "INNER JOIN Customer C ON A.Customer = C.Id "
				+ "WHERE C.Id = %d "
				+ "GROUP BY M.Id "
				+ "ORDER BY COUNT(R.OrderId) desc) "
				+ "LIMIT 10;", Integer.parseInt(customerID));
		
		System.out.println(query);
		
		ResultSet rs = performMySQLQuery(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					Movie movie = new Movie();
					
					movie.setMovieID(Integer.parseInt(rs.getString("Id")));
					movie.setMovieName(rs.getString("Name"));
					movie.setMovieType(rs.getString("Type"));
					movie.setRating(Integer.parseInt(rs.getString("Rating")));
					// Shouldn't this be a double???
					movie.setDistFee((int) Double.parseDouble(rs.getString("DistrFee")));
					movie.setNumCopies(Integer.parseInt(rs.getString("NumCopies")));
					
					movies.add(movie);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
				
		/*Sample data begins
		for (int i = 0; i < 6; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movie.setDistFee(10000);
			movie.setNumCopies(3);
			movie.setRating(5);
			movies.add(movie);
		}
		Sample data ends*/
		
		return movies;

	}

	
}