package bookmark_spring_boot.entities;

import java.util.Arrays;

import bookmark_spring_boot.constants.KidFriendlyStatus;
import bookmark_spring_boot.constants.MovieGenre;

public class Movie extends Bookmark {
	private String imageUrl;
	private int releaseYear;
	private String[] cast, directors;
	private MovieGenre genre;
	private double imdbRating;

	private Movie() {}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String[] getCast() {
		return cast;
	}

	public void setCast(String[] cast) {
		this.cast = cast;
	}

	public String[] getDirectors() {
		return directors;
	}

	public void setDirectors(String[] directors) {
		this.directors = directors;
	}

	public MovieGenre getGenre() {
		return genre;
	}

	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}
	
	
	public static Movie newInstance(long id, String title,String imageUrl, String profileUrl, int releaseYear, String[] cast,
			String[] directors, MovieGenre genre, double imdbRating,KidFriendlyStatus kidFriendlyStatus) {
		
		Movie movie = new Movie();
		
		movie.setId(id);
		movie.setTitle(title);
		movie.setImageUrl(imageUrl);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);
		movie.setKidFriendlyStatus(kidFriendlyStatus);

		return movie;
	}

	@Override
	public String toString() {
		return "Movie [releaseYear=" + releaseYear + ", cast=" + Arrays.toString(cast) + ", directors="
				+ Arrays.toString(directors) + ", genre=" + genre + ", imdbRating=" + imdbRating + "]";
	}

	@Override
	public boolean isKidFriendly() {
		if(genre.equals(MovieGenre.THRILLERS) || genre.equals(MovieGenre.HORROR))
			return false;
		return true;
	}

}
