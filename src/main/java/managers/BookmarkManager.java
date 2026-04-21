package managers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import constants.KidFriendlyStatus;
import dao.BookmarkDao;
import entities.Book;
import entities.Bookmark;
import entities.User;
import entities.UserBookmark;
import entities.Weblink;
import util.HttpConnect;
import util.IOUtil;

public class BookmarkManager {

	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	private BookmarkManager() {}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public List<List<Bookmark>> getBookmark(){
		return dao.getBookmark();
	}

	public void storeBookmarking(User u, Bookmark bookmarked) {
		
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setUser(u);
		userBookmark.setBookmark(bookmarked);
		
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
		}
		
		BookmarkDao.storeBookmarking(userBookmark);
		
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus takeDecision, Bookmark bookmark) {
		bookmark.setKidFriendlyStatus(takeDecision);
		bookmark.setKidFriendlyMarkedBy(user);
		BookmarkDao.updateKidFriendlyStatus(bookmark);
		System.out.println("Kid Friendly Status : "+takeDecision+" is marked by "+user.getFirstName()+" ,"+bookmark);
		
	}

	public void share(User user, Bookmark bookmark) {
		
		bookmark.setSharedBy(user);
		
		System.out.println("data to be shared ");
		if(bookmark instanceof Weblink) {
			String data = ((Weblink)bookmark).getItemData();
			System.out.println("Data : "+data+" is shared by "+user.getFirstName());
		}
		else if(bookmark instanceof Book) {
			String data = ((Book)bookmark).getItemData();
			System.out.println("Data : "+data+" is shared by "+user.getFirstName());
		}
		
		BookmarkDao.sharedByInfo(bookmark);
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		
		return BookmarkDao.getBooks(isBookmarked,userId);
	}

	public Bookmark getBook(long bookId) {
		
		return BookmarkDao.getBook(bookId);
	}

	public Bookmark getMovie(long movieId) {
		return BookmarkDao.getMovie(movieId);
	}

	public Collection<Bookmark> getMovies(boolean isBookmarked, long userId) {
		
		return BookmarkDao.getMovies(isBookmarked,userId);
	}
}
