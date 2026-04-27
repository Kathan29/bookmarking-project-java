package bookmark_spring_boot.controllers;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookmark_spring_boot.entities.Bookmark;
import bookmark_spring_boot.entities.User;
import bookmark_spring_boot.managers.BookmarkManager;
import bookmark_spring_boot.managers.UserManager;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShareController {
	private BookmarkManager bookmarkManager;
	private UserManager userManager;
    public ShareController(BookmarkManager bookmarkManager,UserManager userManager) {
        super();
        this.bookmarkManager = bookmarkManager;
        this.userManager = userManager;
    }

	@GetMapping("/share")
	public String shareToPartner(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		Collection<Bookmark> list= bookmarkManager.getSharableBooksWeblinks();
		model.addAttribute("bookmarkList", list);
		return "ShareToPartner";
	}
	
	@GetMapping("/shareBookmark")
	public String shareBookmark(@RequestParam long id,@RequestParam String type ,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		
		long userId = (long)request.getSession().getAttribute("userId");
		User user = userManager.getUser(userId);
		
		Bookmark bookmark = null;
		
		if(type.equals("Book")) {
			bookmark = bookmarkManager.getBook(id);
		}else {
			bookmark = bookmarkManager.getWeblink(id);
		}
		
		bookmarkManager.share(user, bookmark);
		System.out.println("Shared successfully");
		return "HomePage";
	}

}
