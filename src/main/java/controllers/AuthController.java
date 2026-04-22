package controllers;

import java.io.IOException;

import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.UserManager;

/**
 * Servlet implementation class AuthController
 */
@WebServlet(urlPatterns = {"/auth/logout","/auth"})
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(!request.getServletPath().contains("logout")) {
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			User user = UserManager.getInstance().authenticate(email,password);
			if(user!=null) {	
				request.getSession().setAttribute("userId", user.getId());
				request.getSession().setAttribute("name", user.getFirstName());
				request.getSession().setAttribute("userType", user.getUserType().ordinal());
				request.getRequestDispatcher("home").forward(request, response);
			}else {
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}
			
		}else {
			request.getSession().invalidate();
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
