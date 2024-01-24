package resources;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDao;
import dao.MovieDao;
import model.Movie;

/**
 * Servlet implementation class DeleteMovieController
 */
public class DeleteMovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMovieController() {
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
		String movieID = request.getParameter("movieID");
		
		MovieDao movieDao = new MovieDao();
		String result = movieDao.deleteMovie(movieID);
		
		if(result.equals("success")) {
			response.sendRedirect("managerHome.jsp?status=deleteSuccess");
		}
		else {
			response.sendRedirect("managerHome.jsp?status=deleteFailure");
		}
	}

}
