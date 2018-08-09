import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MovieRecommender {

	private String movieFile;
	private String userRatingsFile;
	private int debug = 0; //1: to show info for debugging, not 1: otherwise
	private int knearest = 10;

	static HashMap<Integer, Movie> moviesTable;
	static HashMap<Integer, UserRating> usersTable;
	// static ArrayList<UserRating> users;

	public MovieRecommender(String userRatingsFile, String movieFile) {
		this.movieFile = movieFile;
		this.userRatingsFile = userRatingsFile;
		usersTable = new HashMap<Integer, UserRating>();
		usersTable = readRatingsFile(userRatingsFile);
		moviesTable = readMovieFile(movieFile);
		//this.debug = debug;
		if (debug == 1) {
			System.out.println();
			System.out.println("User Data File : " + userRatingsFile);
			System.out.println("Movie Data File : " + movieFile);
			System.out.println("# of users : " + usersTable.size());
			System.out.println("# of movies : " + moviesTable.size());
		}
	}

	public void reloadData() {
		usersTable = readRatingsFile(userRatingsFile);
		moviesTable = readMovieFile(movieFile);
	}

	/**
	 * @param usernum
	 * This method will test the accuracy of the algorithm
	 */
	public void algorithmAccuracy() {
		// int size = usersTable.size();
		double sum = 0;
		double val = 0;
		int runs = 0;
		for (int i = 1; i < 100; i++) {
			val = algorithmAccuracy(i);
			if (val != -1) {
				sum += val;
				runs++;
			}
		}
		double average = sum / runs;
		double accuracyRate = (100 - average);
		System.out.println("The algorithm had an average accuracy rate of " + accuracyRate + "%" + " on " + runs + " runs.");
	}

	public double algorithmAccuracy(int usernum) {
		// System.out.println(usernum);
		UserRating user = usersTable.get(usernum);
		// System.out.println(user.userId);
		usersTable.remove(usernum);
		UserRating savedUser = new UserRating(user.userId, user.MovieRatingTable);
		// System.out.println("User" + user.userId+ " was deleted from data set");

		HashMap<Integer, Double> UserRatings = user.getRatings();
		// int numMoviesToDelete = 0;
		HashMap<Integer, Double> newRatings = new HashMap<Integer, Double>();
		HashMap<Integer, Double> originalRatings = new HashMap<Integer, Double>();
		int i = 0;
		for (int movieId : UserRatings.keySet()) {
			if (i < UserRatings.size() - 3) {
				// System.out.println(moviesTable.get(movieId).title +" " + "was added");
				newRatings.put(movieId, UserRatings.get(movieId));
			} else {
				// System.out.println(moviesTable.get(movieId).title +" " + "was not added");
				originalRatings.put(movieId, UserRatings.get(movieId));
			}
			i++;
		}
		user.MovieRatingTable = newRatings;

		ArrayList<Movie> RecommendedMovies = this.recommendMovies(user);
		double sum = 0;
		int n = 0;
		// this loop will calculate the MEAN ABSOLUTE PRECENT ERROR for each prediction
		for (Movie m : RecommendedMovies) {
			if (originalRatings.containsKey(m.movieId)) {
				// System.out.println("Movie: " +m.title + " Original Rating: "+
				// originalRatings.get(m.movieId) + " vs. " + "Prediction: "+ m.predictedScore);
				double originalRating = originalRatings.get(m.movieId);
				double prediction = m.predictedScore;
				sum += (Math.abs(originalRating - prediction)) / originalRating;
				// System.out.println("o:" + originalRating + " p:" + prediction);
				n++;
			}
		}
		double MAPE;
		if (n != 0) {
			sum = sum / n;
			MAPE = sum * 100;
		} else {
			MAPE = -1;
		}

		usersTable.put(savedUser.userId, savedUser);

		System.out.println();
		/*
		 * if (MAPE != -1) System.out.println("Algorithm was " + (100 - MAPE) +
		 * "% accurate on run #" + usernum);
		 */
		// reloadData();
		return MAPE;
	}

	public ArrayList<Movie> recommendMovies(UserRating user) {
		// Step 1: Find k nearest neighbors for the given user
		ArrayList<UserRating> neighbors = findNearestNeighbors(user, this.knearest);
		if (this.debug == 1) {
			System.out.println("User #" + user.userId + " Nearest neighbors: ");
			System.out.println("Neighbor Id    Similarity Score");
			for (UserRating u : neighbors) {
				if (u.simscore != SimilarityScore(user, u)) {
					System.out.println("Error: something wrong with similarity score!");
					break;
				}
				System.out.println(u.userId + " " + u.simscore);
			}

		}
		// Step 2: find movie score predictions based on user's k nearest neighbors
		HashMap<Movie, Double> moviesPredicted = predictScores(user, neighbors);
		if (this.debug == 1) {
			System.out.println("Movie Score Predictions: ");

			for (Movie m : moviesPredicted.keySet()) {
				System.out.printf("Movie: %s Score Prediction: %.2f \n", m.title, moviesPredicted.get(m));
			}
		}
		ArrayList<Movie> moviePredictions = new ArrayList<Movie>();
		for (Movie m : moviesPredicted.keySet()) {
			// System.out.printf("Movie: %s Score Prediction: %.2f \n", m.title,
			// moviesPredicted.get(m) );
			m.predictedScore = moviesPredicted.get(m);
			moviePredictions.add(m);
		}

		// Sort movies based on predicted score
		Collections.sort(moviePredictions);
		return moviePredictions;
	}

	public ArrayList<Movie> recommendMovies(int usernum) {
		UserRating user = usersTable.get(usernum);

		return this.recommendMovies(user);
	}

	private static HashMap<Integer, UserRating> readRatingsFile(String file) {
		File ratingsFile = new File(file);

		Scanner scan = null;

		HashMap<Integer, UserRating> usersList = new HashMap<Integer, UserRating>();
		try {
			scan = new Scanner(ratingsFile);

			int userCount = 0;
			UserRating user = null;
			scan.nextLine();
			int pastId = -1;
			while (scan.hasNext()) {
			//	if (userCount > 20000)
				//	break;

				String line = scan.nextLine();
				String tokens[] = line.split(",");
				int userId = Integer.parseInt(tokens[0]);
				int movieId = Integer.parseInt(tokens[1]);
				double rating = Double.parseDouble(tokens[2]);
				// System.out.println(userId+" ");

				if (userId != pastId) {
					if (user != null) {

						// System.out.println(user.userId+" ");
						// usersTable.put(user.userId, user);
						usersList.put(user.userId, user);
						// System.out.println(usersTable.get(user.userId).userId);
					}
					pastId = userId;

					// System.out.println(userId + " " + userCount);
					user = new UserRating(userId);
					userCount++;

				}
				user.addRating(movieId, rating);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usersList;

	}

	private static HashMap<Integer, Movie> readMovieFile(String file) {
		File movieFile = new File(file);
		HashMap<Integer, Movie> movies = new HashMap<Integer, Movie>();
		Scanner scan = null;
		Movie movie = null;
		try {
			scan = new Scanner(movieFile);
			scan.nextLine();
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String tokens[] = line.split(",");
				int movieId = Integer.parseInt(tokens[0]);
				String movieName = tokens[1];
				String genre = tokens[2];
				movie = new Movie(movieId, movieName, genre);
				movies.put(movieId, movie);

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movies;
	}

	private static ArrayList<UserRating> findNearestNeighbors(UserRating user, int kn) {
		HashMap<UserRating, Double> scoreRanking = new HashMap<UserRating, Double>();

		for (Integer uid : usersTable.keySet()) {// changehere
			UserRating u = usersTable.get(uid);
			if (!u.equals(user)) {
				u.simscore = SimilarityScore(user, u);
				scoreRanking.put(u, u.simscore);

				// System.out.println(SimilarityScore(user, u) + " " + u.userName);
			}
		}
		ArrayList<UserRating> nearestNeighbors = new ArrayList<UserRating>();
		// scoreRanking.sort();
		for (int k = 0; k < kn; k++) {
			nearestNeighbors.add(Collections.max(scoreRanking.entrySet(), Map.Entry.comparingByValue()).getKey());
			scoreRanking.remove(Collections.max(scoreRanking.entrySet(), Map.Entry.comparingByValue()).getKey());
		}

		return nearestNeighbors;
	}

	private static double SimilarityScore(UserRating u1, UserRating u2) {
		// calculate euclidean distance between users
		HashMap<Integer, Double> user1Ratings = u1.getRatings();
		HashMap<Integer, Double> user2Ratings = u2.getRatings();
		double distance = 0;

		int matches = 0;

		for (int key : user1Ratings.keySet()) {

			if (user2Ratings.get(key) != null && user1Ratings.get(key) != null) {
				distance += Math.pow((user1Ratings.get(key) - user2Ratings.get(key)), 2);
				matches++;
			}
		}

		distance = Math.sqrt(distance);
		// distance contains the euclidean distance between the two users
		double similarityScore = 1 / (1 + distance);
		if (matches < 10)
			similarityScore = 0;

		/*
		 * similarityScore is the distance inverted such that a score closer to 1 means
		 * users are similar, and a score close to 0 means they are different (A score
		 * of 1 means the two users are identical)
		 */
		return similarityScore;
	}

	private static HashMap<Movie, Double> predictScores(UserRating user, ArrayList<UserRating> neighbors) {
		// HashMap<Integer, Double> movies= new HashMap<Integer, Double> ();
		ArrayList<Integer> movies = new ArrayList<Integer>();
		HashMap<Integer, Double> userRatings = user.getRatings();
		HashMap<Movie, Double> moviePredictions = new HashMap<Movie, Double>();

		// Step 1: Find movies that user has not seen/rated but its neighbors have.
		for (UserRating other : neighbors) {
			HashMap<Integer, Double> ratings = other.getRatings();

			for (int key : ratings.keySet()) {
				if (!userRatings.containsKey(key)) {
					if (!movies.contains(key))
						movies.add(key);
				}
			}
		}

		// Movies ArrayList now contains all the movies that the user has not seen/rated
		// and that its neighbors have.

		// Step 2: take an weighted average for each movie from how each of the
		// neighbors rated based on the similarity score
		// it.
		for (int movieId : movies) {
			int otherCount = 0; // counts how many of the neighbors rated this particular movie
			double weightedSum = 0; // for weighted average based on the similarity score in order to improve
									// accuracy. (Hopefully!)
			double similaritySum = 0;
			double weightedScore = 0;
			for (UserRating other : neighbors) {
				HashMap<Integer, Double> ratings = other.getRatings();
				if (ratings.containsKey(movieId)) {
					otherCount++;
					weightedSum += ratings.get(movieId) * other.simscore;
					similaritySum += other.simscore;
				}

			}
			if (otherCount > 0) {
				weightedScore = weightedSum / similaritySum;
			}
			if (otherCount > 2) {
				// System.out.printf("MovieId: %d CalculatedScore: %.2f + WeightedScore: %.2f
				// OtherCount: %d\n", movieId,sum, weightedScore, otherCount);
				moviePredictions.put(moviesTable.get(movieId), weightedScore);
			}

		}
		// moviePredictions now contains a key-value pair of movie objects and their
		// score prediction
		return moviePredictions;
	}

}
