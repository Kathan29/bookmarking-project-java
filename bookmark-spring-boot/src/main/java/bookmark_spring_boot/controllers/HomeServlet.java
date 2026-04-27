package bookmark_spring_boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class HomeServlet{

	@GetMapping("/home")
	public String home(HttpServletRequest request) {
		if(request.getSession().getAttribute("userId")==null) {
			return "Login";
		}
		
		return "HomePage";
	}

}
