package entities;

import java.util.Arrays;

import constants.BookGenre;
import constants.KidFriendlyStatus;
import partners.Sharable;

public class Book extends Bookmark implements Sharable{
	private String imageUrl;
	private int publicationYear;
	private String publisher;
	private BookGenre genre;
	private String[] authors;
	private double amazonRating;
	
	private Book() {}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public BookGenre getGenre() {
		return genre;
	}

	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	
	public static Book newInstance(long id, String title,String imageUrl, int publicationYear, String publisher, BookGenre genre, String[] authors,
			double amazonRating,KidFriendlyStatus kidFriendlyStatus) {
		
		Book book = new Book();
		
		book.setId(id);
		book.setTitle(title);
		book.setImageUrl(imageUrl);
		book.setPublicationYear(publicationYear);
		book.setGenre(genre);
		book.setAuthors(authors);
		book.setAmazonRating(amazonRating);
		book.setPublisher(publisher);
		book.setKidFriendlyStatus(kidFriendlyStatus);
		
		return book;
	}
	

	@Override
	public String toString() {
		return "Book [imageUrl=" + imageUrl + ", publicationYear=" + publicationYear + ", publisher=" + publisher
				+ ", genre=" + genre + ", authors=" + Arrays.toString(authors) + ", amazonRating=" + amazonRating + "]";
	}

	@Override
	public boolean isKidFriendly() {
		
		if(genre.equals(BookGenre.SELF_HELP) || genre.equals(BookGenre.PHILOSOPHY))
			return false;
		
		return true;
	}

	@Override
	public String getItemData() {
		StringBuilder xmlData = new StringBuilder();
		
		xmlData.append("<item>");
			xmlData.append("<type>Book</type>");
			xmlData.append("<title>"+getTitle()+"</title>");
			xmlData.append("<publicationYear>"+publicationYear+"</publicationYear>");
			xmlData.append("<publisher>"+publisher+"</publisher>");
			xmlData.append("<authors>"+String.join(",", authors)+"</authors>");
			xmlData.append("<amazonRating>"+amazonRating+"</amazonRating>");
		xmlData.append("</item>");
		
		return xmlData.toString();
	}

}
