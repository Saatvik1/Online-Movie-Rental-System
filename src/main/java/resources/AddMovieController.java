package resources;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import dao.EmployeeDao;
import dao.LoginDao;
import model.Employee;
import model.Login;
import dao.MovieDao;
import model.Movie;

/**
 * Servlet implementation class AddMovieController
 */
public class AddMovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovieController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String movieName = request.getParameter("movieName");
		String movieType = request.getParameter("movieType");
		int movieRating = Integer.parseInt(request.getParameter("movieRating"));
		int movieDistrFee = Integer.parseInt(request.getParameter("movieDistrFee"));
		int movieNumCopies = Integer.parseInt(request.getParameter("movieNumCopies"));
		
		Movie movie = new Movie();
		movie.setMovieName(movieName);
		movie.setMovieType(movieType);
		movie.setRating(movieRating);
		movie.setDistFee(movieDistrFee);
		movie.setNumCopies(movieNumCopies);
		
		MovieDao movieDao = new MovieDao();
		String result = movieDao.addMovie(movie);
		
		if(result.equals("success")) {
			response.sendRedirect("managerHome.jsp?status=addMovieSuccess");
		}
		else {
			response.sendRedirect("addMovie.jsp?status=error");
		}
	}

}
