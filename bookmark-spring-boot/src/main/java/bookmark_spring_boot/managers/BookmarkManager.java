package bookmark_spring_boot.managers;

import java.util.Collection;

import org.springframework.stereotype.Service;

import bookmark_spring_boot.constants.KidFriendlyStatus;
import bookmark_spring_boot.dao.BookmarkDao;
import bookmark_spring_boot.entities.Book;
import bookmark_spring_boot.entities.Bookmark;
import bookmark_spring_boot.entities.User;
import bookmark_spring_boot.entities.UserBookmark;
import bookmark_spring_boot.entities.Weblink;

@Service
public class BookmarkManager {
	private BookmarkDao dao;
	public BookmarkManager(BookmarkDao dao) {
		this.dao = dao;
	}

	public void storeBookmarking(User u, Bookmark bookmarked) {
		
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setUser(u);
		userBookmark.setBookmark(bookmarked);
		
		/*
		//We check if it is weblink , then we download it and store in disk
		if(bookmarked instanceof Weblink) {
			String url = ((Weblink)bookmarked).getUrl();
			if(!url.endsWith(".pdf")) {
				try {
					String webpage = HttpConnect.download(url);
					if(webpage!=null) {
						IOUtil.write(webpage,bookmarked.getId());
					}
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		
		dao.storeBookmarking(userBookmark);
		
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus takeDecision, Bookmark bookmark) {
		
		bookmark.setKidFriendlyStatus(takeDecision);
		bookmark.setKidFriendlyMarkedBy(user);
		dao.updateKidFriendlyStatus(bookmark);
		System.out.println("Kid Friendly Status : "+takeDecision+" is marked by "+user.firstName()+" ,"+bookmark);
		
	}

	public void share(User user, Bookmark bookmark) {
		
		bookmark.setSharedBy(user);
		
		System.out.println("data to be shared ");
		if(bookmark instanceof Weblink w) {
			String data = w.getItemData();
			System.out.println("Data : "+data+" is shared by "+user.firstName());
		}
		else if(bookmark instanceof Book b) {
			String data = b.getItemData();
			System.out.println("Data : "+data+" is shared by "+user.firstName());
		}
		
		dao.sharedByInfo(bookmark);
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		
		return dao.getBooks(isBookmarked,userId);
	}

	public Bookmark getBook(long bookId) {
		
		return dao.getBook(bookId);
	}

	public Bookmark getMovie(long movieId) {
		return dao.getMovie(movieId);
	}

	public Collection<Bookmark> getMovies(boolean isBookmarked, long userId) {
		
		return dao.getMovies(isBookmarked,userId);
	}

	public Bookmark getWeblink(long weblinkId) {
	
		return dao.getWeblink(weblinkId);
	}

	public Collection<Bookmark> getWeblinks(boolean isBookmarked, long userId) {
	
		return dao.getWeblinks(isBookmarked,userId);
	}

	public void deleteBookmarking(long userId, Bookmark bookmark) {
		
		dao.deleteBookmarking(userId,bookmark);
	}

	public Collection<Bookmark> getSharableBooksWeblinks() {
		
		return dao.getSharableBooksWeblinks();
	}
}
