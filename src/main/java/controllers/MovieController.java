package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.BookmarkManager;
import managers.UserManager;

import java.io.IOException;
import java.util.Collection;

import constants.KidFriendlyStatus;
import entities.Bookmark;
import entities.User;

/**
 * Servlet implementation class MovieController
 */
@WebServlet(urlPatterns = {"/saveMovie","/myMovie","/deleteMovie","/movie","/approveMovieKF","/rejectMovieKF"})
public class MovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userId") != null) {
			long userId = (long)request.getSession().getAttribute("userId");
			
			
			if(request.getServletPath().contains("saveMovie")) {
				
				long movieId = Long.parseLong(request.getParameter("mid"));
				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = BookmarkManager.getInstance().getMovie(movieId);
				
				BookmarkManager.getInstance().storeBookmarking(user, bookmark);
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getMovies(true,userId); 
				request.setAttribute("movies", list);
				request.getRequestDispatcher("/MyMovies.jsp").forward(request,response);
				
			}
			else if(request.getServletPath().contains("myMovie")) {
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getMovies(true,userId); 
				request.setAttribute("movies", list);
				request.getRequestDispatcher("/MyMovies.jsp").forward(request, response);
			}
			else if(request.getServletPath().contains("deleteMovie")) {
				
				long movieId = Long.parseLong(request.getParameter("mid"));
				Bookmark bookmark = BookmarkManager.getInstance().getMovie(movieId);
				
				BookmarkManager.getInstance().deleteBookmarking(userId, bookmark);
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getMovies(true,userId); 
				request.setAttribute("movies", list);
				request.getRequestDispatcher("/MyMovies.jsp").forward(request,response);
			}
			else if(request.getServletPath().contains("approveMovieKF")) {
				
				long movieId = Long.parseLong(request.getParameter("mid"));
				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = BookmarkManager.getInstance().getMovie(movieId);
				BookmarkManager.getInstance().setKidFriendlyStatus(user, KidFriendlyStatus.APPROVED, bookmark);
				
				response.sendRedirect(request.getContextPath() + "/movie");
			}
			else if(request.getServletPath().contains("rejectMovieKF")) {
				long movieId = Long.parseLong(request.getParameter("mid"));
				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = BookmarkManager.getInstance().getMovie(movieId);
				BookmarkManager.getInstance().setKidFriendlyStatus(user, KidFriendlyStatus.REJECTED, bookmark);
				
				response.sendRedirect(request.getContextPath() + "/movie");
			}
			else{
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getMovies(false,userId); 
				request.setAttribute("movies", list);
				request.getRequestDispatcher("/ViewMovies.jsp").forward(request, response);
			}
		}
		
		else {
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
