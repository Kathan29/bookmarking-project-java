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
public class WeblinkController {
	private BookmarkManager bookmarkManager;
	private UserManager userManager;

	public WeblinkController(BookmarkManager bookmarkManager, UserManager userManager) {
		super();
		this.bookmarkManager = bookmarkManager;
		this.userManager = userManager;
	}

	@GetMapping("/weblink")
	public String weblink(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("userId") == null) {
			return "Login";
		}

		long userId = (long) request.getSession().getAttribute("userId");
		Collection<Bookmark> list = bookmarkManager.getWeblinks(false, userId);
		model.addAttribute("weblinks", list);
		return "ViewWeblinks";
	}

	@GetMapping("/saveWeblink")
	public String saveWeblink(@RequestParam long wid, Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("userId") == null) {
			return "Login";
		}

		long userId = (long) request.getSession().getAttribute("userId");

		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getWeblink(wid);

		bookmarkManager.storeBookmarking(user, bookmark);

		return "redirect:/myWeblink";
	}

	@GetMapping("/myWeblink")
	public String myWeblink(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("userId") == null) {
			return "Login";
		}

		long userId = (long) request.getSession().getAttribute("userId");
		Collection<Bookmark> list = bookmarkManager.getWeblinks(true, userId);
		model.addAttribute("weblinks", list);
		return "MyWeblinks";
	}

	@GetMapping("/deleteWeblink")
	public String deleteWeblink(@RequestParam long wid, Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("userId") == null) {
			return "Login";
		}

		long userId = (long) request.getSession().getAttribute("userId");
		Bookmark bookmark = bookmarkManager.getWeblink(wid);

		bookmarkManager.deleteBookmarking(userId, bookmark);

		return "redirect:/myWeblink";
	}

	@GetMapping("/approveWeblinkKF")
	public String approveKidFriendly(@RequestParam long wid, Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		long userId = (long) request.getSession().getAttribute("userId");
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getWeblink(wid);
		bookmarkManager.setKidFriendlyStatus(user, KidFriendlyStatus.APPROVED, bookmark);

		return "redirect:/weblink";
	}

	@GetMapping("/rejectWeblinkKF")
	public String rejectKidFriendly(@RequestParam long wid, Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("userId") == null) {
			return "Login";
		}
		long userId = (long) request.getSession().getAttribute("userId");
		User user = userManager.getUser(userId);
		Bookmark bookmark = bookmarkManager.getWeblink(wid);
		bookmarkManager.setKidFriendlyStatus(user, KidFriendlyStatus.REJECTED, bookmark);

		return "redirect:/weblink";
	}
}
