package controllers;

import constants.KidFriendlyStatus;
import entities.Bookmark;
import entities.User;
import managers.BookmarkManager;




public class BookmarkController{

	public BookmarkController() {
		super();
	}

	/*
	public void storeBookmarking(User u, Bookmark bookmarked) {
		BookmarkManager.getInstance().storeBookmarking(u,bookmarked);
	}*/
	public void setKidFriendlyStatus(User user, KidFriendlyStatus takeDecision, Bookmark bookmark) {
		
		BookmarkManager.getInstance().setKidFriendlyStatus(user,takeDecision,bookmark);
		
	}

	public void share(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().share(user,bookmark);
		
	}
	
	
}
