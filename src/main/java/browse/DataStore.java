package browse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import constants.BookGenre;
import constants.Gender;
import constants.MovieGenre;
import constants.UserType;
import entities.Book;
import entities.Bookmark;
import entities.Movie;
import entities.User;
import entities.Weblink;

public class DataStore {

	private static List<User> user = new ArrayList<>();
	private static List<List<Bookmark>> bookmark = new ArrayList<>();
	//private static List<UserBookmark> userbookmarks = new ArrayList<>();

	public static List<User> getUser() {
		return user;
	}

	public static List<List<Bookmark>> getBookmark() {
		return bookmark;
	}

	public static void loadData() {
		/*
		 * loadUsers(); loadBook(); loadMovie(); loadWebLink();
		 */

		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrill_io", "root", "root");
				Statement stmt = conn.createStatement();) {

			loadUsers(stmt);
			loadWebLink(stmt);
			loadMovie(stmt);
			loadBook(stmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void loadWebLink(Statement stmt) throws SQLException {

		String query = "Select * from weblink";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			var id = rs.getLong("id");
			var title = rs.getString("title");
			var imageUrl = rs.getString("image_url");
			var url = rs.getString("url");
			var host = rs.getString("host");
			Bookmark webBookmark = Weblink.newInstance(id,title,imageUrl,url,host);
			bookmarkList.add(webBookmark);
		}

		bookmark.add(bookmarkList);

	}

	private static void loadMovie(Statement stmt) throws SQLException{

		String query = "Select m.id,title,image_url,release_year,movie_genre_id,imdb_rating,GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast,GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors"
				+" from movie m,director d,movie_director md,actor a, movie_actor ma"
				+" where a.id=ma.actor_id and ma.movie_id=m.id and d.id=md.director_id and md.movie_id=m.id"
				+ " group by m.id";
		ResultSet rs = stmt.executeQuery(query);


		List<Bookmark> bookmarkList = new ArrayList<>();
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

			Bookmark movieBookmark = Movie.newInstance(id,title,imageUrl,"",release_year,cast,directors,genre,imdb_rating);
			bookmarkList.add(movieBookmark);
		}

		bookmark.add(bookmarkList);

	}

	private static void loadBook(Statement stmt) throws SQLException {

		String query = "select b.id as id,title,image_url,publication_year,p.name as publisher,book_genre_id,amazon_rating,GROUP_CONCAT(a.name SEPARATOR ',') AS authors"
				+ " from book b,publisher p,author a,book_author ba"
				+ " where b.publisher_id=p.id and b.id=ba.book_id and a.id=ba.author_id"
				+ " group by b.id";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			var id = rs.getLong("id");
			var title = rs.getString("title");
			var imageUrl = rs.getString("image_url");
			var publication_year = rs.getInt("publication_year");
			var publisher = rs.getString("publisher");
			var authors = rs.getString("authors").split(",");
			var bookGenreId = rs.getInt("book_genre_id");
			BookGenre genre = BookGenre.values()[bookGenreId];
			var amazon_rating = rs.getDouble("amazon_rating");

			Bookmark bookBookmark = Book.newInstance(id,title,imageUrl,publication_year,publisher,genre,authors,amazon_rating);
			bookmarkList.add(bookBookmark);
		}
		bookmark.add(bookmarkList);
	}

	private static void loadUsers(Statement stmt) throws SQLException {

		String query = "Select * from user";
		ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {

			long id = rs.getLong("id");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			int gender_id = rs.getInt("gender_id");
			Gender gender = Gender.values()[gender_id];
			int user_type_id = rs.getInt("user_type_id");
			UserType userType = UserType.values()[user_type_id];

			User newUser = User.newInstance(id, email, password, firstName, lastName, userType,
					gender);
			user.add(newUser);
		}

	}



}
