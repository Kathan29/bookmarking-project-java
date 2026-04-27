package bookmark_spring_boot.controllers;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookmark_spring_boot.constants.KidFriendlyStatus;
import bookmark_spring_boot.entities.Bookmark;
import bookmark_spring_boot.entities.User;
import bookmark_spring_boot.managers.BookmarkManager;
import bookmark_spring_boot.managers.UserManager;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MovieController{
	private BookmarkManager bookmarkManager;
	private UserManager userManager;
    public MovieController(BookmarkManager bookmarkManager,UserManager userManager) {
        super();
        this.bookmarkManager = bookmarkManager;
        this.userManager = userManager;
        
    }

	@GetMapping("/movie")
	public String movie(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		Collection<Bookmark> list = bookmarkManager.getMovies(false,userId); 
		model.addAttribute("movies", list);
		return "ViewMovies";
	}
	
	@GetMapping("/saveMovie")
	public String saveMovie(@RequestParam long mid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getMovie(mid);
		
		bookmarkManager.storeBookmarking(user, bookmark);
		
		return "redirect:/myMovie";
	}
	
	@GetMapping("/myMovie")
	public String myMovie(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		Collection<Bookmark> list = bookmarkManager.getMovies(true,userId); 
		model.addAttribute("movies", list);
		return "MyMovies";
	}
	
	@GetMapping("/deleteMovie")
	public String deleteMovie(@RequestParam long mid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		Bookmark bookmark = bookmarkManager.getMovie(mid);
		
		bookmarkManager.deleteBookmarking(userId,bookmark);
		
		return "redirect:/myMovie";
	}
	
	@GetMapping("/approveMovieKF")
	public String approveKidFriendly(@RequestParam long mid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		long userId = (long)request.getSession().getAttribute("userId");
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getMovie(mid);
		bookmarkManager.setKidFriendlyStatus(user, KidFriendlyStatus.APPROVED, bookmark);
		
		return "redirect:/movie";
	}
	
	@GetMapping("/rejectMovieKF")
	public String rejectKidFriendly(@RequestParam long mid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		long userId = (long)request.getSession().getAttribute("userId");
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getMovie(mid);
		bookmarkManager.setKidFriendlyStatus(user, KidFriendlyStatus.REJECTED, bookmark);
		
		return "redirect:/movie";
	}

}
