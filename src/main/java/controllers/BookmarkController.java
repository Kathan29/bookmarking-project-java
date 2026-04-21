package controllers;

import java.io.IOException;
import java.util.Collection;

import constants.KidFriendlyStatus;
import entities.Bookmark;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.BookmarkManager;
import managers.UserManager;


@WebServlet(urlPatterns = {"/saveBook","/myBook","/book","/movie","/weblink","/myMovie","/saveMovie","/saveWeblink","/myWeblink"})

public class BookmarkController extends HttpServlet{

	public BookmarkController() {
		super();
	}
	

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void storeBookmarking(User u, Bookmark bookmarked) {
		BookmarkManager.getInstance().storeBookmarking(u,bookmarked);
	}
	public void setKidFriendlyStatus(User user, KidFriendlyStatus takeDecision, Bookmark bookmark) {
		
		BookmarkManager.getInstance().setKidFriendlyStatus(user,takeDecision,bookmark);
		
	}

	public void share(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().share(user,bookmark);
		
	}
	
	
}
