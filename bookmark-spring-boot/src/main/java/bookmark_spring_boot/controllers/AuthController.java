package bookmark_spring_boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookmark_spring_boot.entities.User;
import bookmark_spring_boot.managers.UserManager;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController{
	private UserManager userManager;
    public AuthController(UserManager userManager) {
        super();
        this.userManager = userManager;
    }

	
	@GetMapping("/login")
	public String login() {
		return "Login";
	}
	
	@PostMapping("/auth")
	public String authenticate(@RequestParam String email,@RequestParam String password,Model model,HttpServletRequest request) {
		User user = userManager.authenticate(email,password);
		if(user==null) {
			return "Login";
		}	
		request.getSession().setAttribute("userId", user.id());
		request.getSession().setAttribute("name", user.firstName());
		request.getSession().setAttribute("userType", user.userType().ordinal());
		return "HomePage";
	}
	
	@GetMapping("/auth/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "Login";
	}

}
