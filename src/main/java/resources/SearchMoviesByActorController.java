package resources;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.MovieDao;
import model.Movie;
/**
 * Servlet implementation class SearchMoviesByActorController
 */
public class SearchMoviesByActorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMoviesByActorController() {
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
		String actorName = request.getParameter("actorName");
		
		MovieDao movieDao = new MovieDao();
		List<Movie> movies = movieDao.getMoviesByName(actorName);
		
		request.setAttribute("movies", movies);
		
		RequestDispatcher rd = request.getRequestDispatcher("showMoviesForCustomer.jsp");
		rd.forward(request, response);
	}

}
