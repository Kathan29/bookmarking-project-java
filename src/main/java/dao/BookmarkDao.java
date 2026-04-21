package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import browse.DataStore;
import constants.BookGenre;
import constants.MovieGenre;
import entities.Book;
import entities.Bookmark;
import entities.Movie;
import entities.UserBookmark;

public class BookmarkDao {
	public List<List<Bookmark>> getBookmark() {
		return DataStore.getBookmark();
	}

	public static void storeBookmarking(UserBookmark userBookmark) {

		long userId = userBookmark.getUser().getId();
		Bookmark bookmark = userBookmark.getBookmark();

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			if (bookmark instanceof Book) {
				storeUserBookEntry(bookmark.getId(), userId, stmt);
			} else if (bookmark instanceof Movie) {
				storeUserMovieEntry(bookmark.getId(), userId, stmt);
			} else {
				storeUserWeblinkEntry(bookmark.getId(), userId, stmt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void storeUserWeblinkEntry(long weblinkId, long userId, Statement stmt) throws SQLException {
		String query = "insert into user_weblink(user_id,weblink_id) values(" + userId + "," + weblinkId + ");";
		stmt.executeUpdate(query);
	}

	private static void storeUserMovieEntry(long movieId, long userId, Statement stmt) throws SQLException {
		String query = "insert into user_movie(user_id,movie_id) values(" + userId + "," + movieId + ");";
		stmt.executeUpdate(query);
	}

	private static void storeUserBookEntry(long bookId, long userId, Statement stmt) throws SQLException {
		String query = "insert into user_book(user_id,book_id) values(" + userId + "," + bookId + ");";
		stmt.executeUpdate(query);
	}

	public static void updateKidFriendlyStatus(Bookmark bookmark) {

		long userId = bookmark.getKidFriendlyMarkedBy().getId();
		int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();

		String tableToUpdate = "weblink";
		if (bookmark instanceof Book) {
			tableToUpdate = "book";
		} else {
			tableToUpdate = "movie";
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			String query = "update " + tableToUpdate + " set kid_friendly_marked_by = " + userId
					+ " , kid_friendly_status= " + kidFriendlyStatus + " where id=" + bookmark.getId();
			System.out.println("Update query : " + query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sharedByInfo(Bookmark bookmark) {

		long userId = bookmark.getSharedBy().getId();

		String tableToUpdate = "weblink";
		if (bookmark instanceof Book) {
			tableToUpdate = "book";
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			String query = "update " + tableToUpdate + " set shared_by = " + userId + " where id=" + bookmark.getId();
			System.out.println("Update query : " + query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		Collection<Bookmark> result = new ArrayList<Bookmark>();

		try {
			// This line tells the JVM to explicitly load the MySQL driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			String query = "";
			if (!isBookmarked) {
				query = "Select b.id, title, image_url, publication_year,p.name as publisher, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
						+ "amazon_rating from Book b, Author a, Book_Author ba,publisher p where b.publisher_id=p.id and b.id = ba.book_id and ba.author_id = a.id and "
						+ "b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
						+ " and u.id = ub.user_id) group by b.id";
			} else {
				query = "Select b.id, title, image_url, publication_year,p.name as publisher, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
						+ "amazon_rating from Book b, Author a, Book_Author ba,publisher p where b.publisher_id=p.id and b.id = ba.book_id and ba.author_id = a.id and "
						+ "b.id IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
						+ " and u.id = ub.user_id) group by b.id";
			}

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				var id = rs.getLong("id");
				var title = rs.getString("title");
				var image_url = rs.getString("image_url");
				var publication_year = rs.getInt("publication_year");
				var publisher = rs.getString("publisher");
				var authors = rs.getString("authors").split(",");
				var bookGenreId = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[bookGenreId];
				var amazon_rating = rs.getDouble("amazon_rating");
				// System.out.println("id: " + id + ", title: " + title + ", publication year: "
				// + publication_year + ", authors: " + String.join(", ", authors) + ", genre: "
				// + genre + ", amazonRating: " + amazon_rating);
				Bookmark bookBookmark = Book.newInstance(id, title, image_url, publication_year, publisher, genre,
						authors, amazon_rating);
				result.add(bookBookmark);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public static Bookmark getBook(long bookId) {
		Bookmark bookBookmark = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			String query = "Select b.id, title, image_url, publication_year,p.name as publisher, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
					+ "amazon_rating from Book b, Author a, Book_Author ba,publisher p where b.publisher_id=p.id and b.id = ba.book_id and ba.author_id = a.id and "
					+ "b.id =" + bookId + " group by b.id";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				var id = rs.getLong("id");
				var title = rs.getString("title");
				var image_url = rs.getString("image_url");
				var publication_year = rs.getInt("publication_year");
				var publisher = rs.getString("publisher");
				var authors = rs.getString("authors").split(",");
				var bookGenreId = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[bookGenreId];
				var amazon_rating = rs.getDouble("amazon_rating");
				// System.out.println("id: " + id + ", title: " + title + ", publication year: "
				// + publication_year + ", authors: " + String.join(", ", authors) + ", genre: "
				// + genre + ", amazonRating: " + amazon_rating);
				bookBookmark = Book.newInstance(id, title, image_url, publication_year, publisher, genre, authors,
						amazon_rating);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookBookmark;
	}

	public static Bookmark getMovie(long movieId) {
		Bookmark bookmark = null;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			String query = "Select m.id,title,image_url,release_year,movie_genre_id,imdb_rating,GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast,GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors"
					+ " from movie m,director d,movie_director md,actor a, movie_actor ma"
					+ " where a.id=ma.actor_id and ma.movie_id=m.id and d.id=md.director_id and md.movie_id=m.id and m.id="
					+ movieId + " group by m.id";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				var id = rs.getLong("id");
				var title = rs.getString("title");
				var imageUrl = rs.getString("image_url");
				var release_year = rs.getInt("release_year");
				var genreId = rs.getInt("movie_genre_id");
				MovieGenre genre = MovieGenre.values()[genreId];
				var imdb_rating = rs.getDouble("imdb_rating");
				var cast = rs.getString("cast").split(",");
				var directors = rs.getString("directors").split(",");

				bookmark = Movie.newInstance(id, title, imageUrl, "", release_year, cast, directors, genre,
						imdb_rating);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bookmark;
	}

	public static Collection<Bookmark> getMovies(boolean isBookmarked, long userId) {

		Collection<Bookmark> result = new ArrayList<Bookmark>();

		try {
			// This line tells the JVM to explicitly load the MySQL driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			String query = "";
			if (!isBookmarked) {
				query = "Select m.id,title,image_url,release_year,movie_genre_id,imdb_rating,GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast,GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors"
						+ " from movie m,director d,movie_director md,actor a, movie_actor ma"
						+ " where a.id=ma.actor_id and ma.movie_id=m.id and d.id=md.director_id and md.movie_id=m.id and"
						+ " m.id NOT IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
						+ " and u.id = um.user_id) group by m.id";
			} else {
				query = "Select m.id,title,image_url,release_year,movie_genre_id,imdb_rating,GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast,GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors"
						+ " from movie m,director d,movie_director md,actor a, movie_actor ma"
						+ " where a.id=ma.actor_id and ma.movie_id=m.id and d.id=md.director_id and md.movie_id=m.id and"
						+ " m.id IN (select um.movie_id from User u, User_Movie um where u.id = " + userId
						+ " and u.id = um.user_id) group by m.id";
			}

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				var id = rs.getLong("id");
				var title = rs.getString("title");
				var imageUrl = rs.getString("image_url");
				var release_year = rs.getInt("release_year");
				var genreId = rs.getInt("movie_genre_id");
				MovieGenre genre = MovieGenre.values()[genreId];
				var imdb_rating = rs.getDouble("imdb_rating");
				var cast = rs.getString("cast").split(",");
				var directors = rs.getString("directors").split(",");

				Bookmark bookmark = Movie.newInstance(id, title, imageUrl, "", release_year, cast, directors, genre,
						imdb_rating);
				result.add(bookmark);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
