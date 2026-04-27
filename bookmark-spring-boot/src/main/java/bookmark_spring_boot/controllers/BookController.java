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
public class BookController {
	private BookmarkManager bookmarkManager;
	private UserManager userManager;
    public BookController(BookmarkManager bookmarkManager,UserManager userManager) {
        super();
       this.bookmarkManager = bookmarkManager;
       this.userManager = userManager;
    }

	@GetMapping("/book")
	public String book(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		Collection<Bookmark> list = bookmarkManager.getBooks(false,userId); 
		model.addAttribute("books", list);
		return "ViewBooks";
	}
	
	@GetMapping("/saveBook")
	public String saveBook(@RequestParam long bid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getBook(bid);
		
		bookmarkManager.storeBookmarking(user, bookmark);
		
		return "redirect:/myBook";
	}
	
	@GetMapping("/myBook")
	public String myBook(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		Collection<Bookmark> list = bookmarkManager.getBooks(true,userId); 
		model.addAttribute("books", list);
		return "MyBooks";
	}
	
	@GetMapping("/deleteBook")
	public String deleteBook(@RequestParam long bid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		Bookmark bookmark = bookmarkManager.getBook(bid);
		
		bookmarkManager.deleteBookmarking(userId,bookmark);
		
		return "redirect:/myBook";
	}
	
	@GetMapping("/approveBookKF")
	public String approveKidFriendly(@RequestParam long bid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		long userId = (long)request.getSession().getAttribute("userId");
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getBook(bid);
		bookmarkManager.setKidFriendlyStatus(user, KidFriendlyStatus.APPROVED, bookmark);
		
		return "redirect:/book";
	}
	
	@GetMapping("/rejectBookKF")
	public String rejectKidFriendly(@RequestParam long bid,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		long userId = (long)request.getSession().getAttribute("userId");
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getBook(bid);
		bookmarkManager.setKidFriendlyStatus(user, KidFriendlyStatus.REJECTED, bookmark);
		
		return "redirect:/book";
	}
}
