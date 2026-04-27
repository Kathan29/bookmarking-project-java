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
 * Servlet implementation class ShareController
 */
@WebServlet(urlPatterns = {"/share","/shareBookmark"})
public class ShareController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userId") != null) {
			long userId = (long)request.getSession().getAttribute("userId");
			
			if(request.getServletPath().contains("shareBookmark")) {
				
				User user = UserManager.getInstance().getUser(userId);
				long id = Long.parseLong(request.getParameter("id"));
				String bookmarkType = request.getParameter("type");
				Bookmark bookmark = null;
				
				if(bookmarkType.equals("Book")) {
					bookmark = BookmarkManager.getInstance().getBook(id);
				}else {
					bookmark = BookmarkManager.getInstance().getWeblink(id);
				}
				
				BookmarkManager.getInstance().share(user, bookmark);
				System.out.println("Shared successfully");
				request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			}else {
				Collection<Bookmark> list= BookmarkManager.getInstance().getSharableBooksWeblinks();
				request.setAttribute("bookmarkList", list);
				request.getRequestDispatcher("/ShareToPartner.jsp").forward(request, response);
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
