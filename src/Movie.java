import java.util.HashMap;

public class Movie implements Comparable<Movie> {

	int movieId;
	String title;
	String genre;
	double predictedScore;

	public Movie(int movieId, String title, String genre) {

		this.movieId = movieId;
		this.title = title;
		this.genre = genre;

	}

	public int compareTo(Movie m) {

		return Double.compare(m.predictedScore, this.predictedScore);

	}

}
