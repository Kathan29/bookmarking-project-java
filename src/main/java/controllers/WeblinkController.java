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
 * Servlet implementation class WeblinkController
 */
@WebServlet(urlPatterns = {"/saveWeblink","/myWeblink","/deleteWeblink","/weblink","/approveWebKF","/rejectWebKF"})
public class WeblinkController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeblinkController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userId") != null) {
			long userId = (long)request.getSession().getAttribute("userId");
			
			
			if(request.getServletPath().contains("saveWeblink")) {
				
				long weblinkId = Long.parseLong(request.getParameter("wid"));
				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = BookmarkManager.getInstance().getWeblink(weblinkId);
				
				BookmarkManager.getInstance().storeBookmarking(user, bookmark);
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getWeblinks(true,userId); 
				request.setAttribute("weblinks", list);
				request.getRequestDispatcher("/MyWeblinks.jsp").forward(request,response);
				
			}
			else if(request.getServletPath().contains("myWeblink")) {
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getWeblinks(true,userId); 
				request.setAttribute("weblinks", list);
				request.getRequestDispatcher("/MyWeblinks.jsp").forward(request, response);
			}
			else if(request.getServletPath().contains("deleteWeblink")) {
				long weblinkId = Long.parseLong(request.getParameter("wid"));

				Bookmark bookmark = BookmarkManager.getInstance().getWeblink(weblinkId);
				
				BookmarkManager.getInstance().deleteBookmarking(userId, bookmark);
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getWeblinks(true,userId); 
				request.setAttribute("weblinks", list);
				request.getRequestDispatcher("/MyWeblinks.jsp").forward(request,response);
			}
			else if(request.getServletPath().contains("approveWebKF")) {
				
				long weblinkId = Long.parseLong(request.getParameter("wid"));
				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = BookmarkManager.getInstance().getWeblink(weblinkId);
				BookmarkManager.getInstance().setKidFriendlyStatus(user, KidFriendlyStatus.APPROVED, bookmark);
				
				response.sendRedirect(request.getContextPath() + "/weblink");
			}
			else if(request.getServletPath().contains("rejectWebKF")) {
				long weblinkId = Long.parseLong(request.getParameter("wid"));
				User user = UserManager.getInstance().getUser(userId);
				Bookmark bookmark = BookmarkManager.getInstance().getWeblink(weblinkId);
				BookmarkManager.getInstance().setKidFriendlyStatus(user, KidFriendlyStatus.REJECTED, bookmark);
				
				response.sendRedirect(request.getContextPath() + "/weblink");
			}
			else{
				
				Collection<Bookmark> list = BookmarkManager.getInstance().getWeblinks(false,userId); 
				request.setAttribute("weblinks", list);
				request.getRequestDispatcher("/ViewWeblinks.jsp").forward(request, response);
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
