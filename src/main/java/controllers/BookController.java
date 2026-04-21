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

import entities.Bookmark;
import entities.User;

/**
 * Servlet implementation class BookController
 */
@WebServlet(urlPatterns = {"/saveBook","/myBook","/book"})
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userId") != null) {
			long userId = (long)request.getSession().getAttribute("userId");
			
			
			if(request.getServletPath().contains("saveBook")) {
				
				long bookId = Long.parseLong(request.getParameter("bid"));
				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = BookmarkManager.getInstance().getBook(bookId);
				
				BookmarkManager.getInstance().storeBookmarking(user, bookmark);
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true,userId); 
				request.setAttribute("books", list);
				request.getRequestDispatcher("/MyBooks.jsp").forward(request,response);
				
			}
			else if(request.getServletPath().contains("myBook")) {
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true,userId); 
				request.setAttribute("books", list);
				request.getRequestDispatcher("/MyBooks.jsp").forward(request, response);
			}
			else if(request.getServletPath().contains("book")){
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(false,userId); 
				request.setAttribute("books", list);
				request.getRequestDispatcher("/ViewBooks.jsp").forward(request, response);
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
