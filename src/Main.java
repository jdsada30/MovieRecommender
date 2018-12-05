import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	public static void main ( String[] args ) {
		
		MovieRecommender recommender = new MovieRecommender("rating.csv", "movie.csv"); 
		
		//recommender.algorithmAccuracy();
		//
		//recommender.algorithmInsights();
		/*UserRating user1 = new UserRating(0); 
		user1.addRating(858, 5);
		user1.addRating(1201, 5);
		user1.addRating(4327, 5);
		user1.addRating(1387, 5);
		user1.addRating(1036, 4);
		user1.addRating(2403, 4);
		user1.addRating(260, 3);
		user1.addRating(4896, 2);
		user1.addRating(5349, 1);
		user1.addRating(3702, 4);
		user1.addRating(3873, 3);
		user1.addRating(4328, 3);
		user1.addRating(2402, 4);
		user1.addRating(2409, 3);		
		user1.addRating(1028, 2);
		user1.addRating(2949, 5);
		user1.addRating(2948, 5);
		user1.addRating(2947, 4);
		user1.addRating(5418, 5);
		user1.addRating(8665, 5);
		user1.addRating(54286, 5);
		user1.addRating(118916, 2);*/
		
		UserRating user2 = new UserRating(0); 
		user2.addRating(858, 5);
		user2.addRating(1028, 3);
		user2.addRating(1721, 4);
		user2.addRating(7064, 5);
		user2.addRating(1172, 5);
		user2.addRating(2324, 5);
		user2.addRating(527, 4);
		user2.addRating(64034 ,5);
		user2.addRating(60397 ,4);
		user2.addRating(3545 ,4);
		user2.addRating(4681 ,4);
		user2.addRating(597, 4);
		user2.addRating(1955, 4);
		user2.addRating(4857, 4);
		user2.addRating(45720, 4);
		user2.addRating(58, 5);
		user2.addRating(1206, 1);
		user2.addRating(33836, 1);
		user2.addRating(2315, 1);
		user2.addRating(5323, 1);
		user2.addRating(7004, 1);
	
		
		

		
		ArrayList<Movie> RecommendedMovies = recommender.recommendMovies(user2); 	
		String fiveStars = "*****"; 
		String fourStars = "****"; 
		String threeStars = "***";
		String twoStars = "**"; 
		String oneStar = "*"; 
		System.out.println("The algorithm predicted the following movies and scores for you: "); 
		for(Movie m : RecommendedMovies) {
			if(Math.round(m.predictedScore)  == 5)
			System.out.println(m.title+ "  "+ fiveStars); 
			else if (Math.round(m.predictedScore)  == 4)
				System.out.println(m.title+ " " + fourStars); 	
			else if (Math.round(m.predictedScore)  == 3)
				System.out.println(m.title+ " " + threeStars); 
			else if (Math.round(m.predictedScore)  == 2)
				System.out.println(m.title+ " " + twoStars); 
			else if (Math.round(m.predictedScore)  == 1)
				System.out.println(m.title+ " " + oneStar); 
		}
		
		
	
	
	
		}
	
	

	
	public static String predictFavoriteGenre(HashMap<Integer, Double> predictedMovies, HashMap<Integer, Movie> movies) {
		
		HashMap<String, Integer> genreLookupTable = new HashMap<String, Integer>(); 
		String genre  = null; 
		for(int movieId : predictedMovies.keySet()) {
			
			if(predictedMovies.get(movieId) > 4) 
		{
			genre = movies.get(movieId).genre; 
			if(genreLookupTable.containsKey(genre)) {
				genreLookupTable.put(genre, genreLookupTable.get(genre)+1); 
			}else {
				genreLookupTable.put(genre, 1); 
			}
			
		}
			
		}
		
		//get genre with max count 
		int max = 0; 
		String favoriteGenre = null; 
		for(String gen : genreLookupTable.keySet()) {
				if(genreLookupTable.get(gen) >max) {
					max = genreLookupTable.get(gen); 
					favoriteGenre = gen; 
				}
		}
		
		
		return favoriteGenre; 
	}
	
	public static String findFavoriteGenre(UserRating user, HashMap<Integer, Movie> movies ) {
		HashMap<Integer, Double>  userRatings= user.getRatings();
		HashMap<String, Integer> genreLookupTable = new HashMap<String, Integer>(); 
		String genre  = null; 
		for(int movieId : userRatings.keySet()) 
	{
			if(userRatings.get(movieId) > 4) {
			genre = movies.get(movieId).genre; 
			if(genreLookupTable.containsKey(genre)) {
				genreLookupTable.put(genre, genreLookupTable.get(genre)+1); 
			}else {
				genreLookupTable.put(genre, 1); 
			}
	}			
		}
		
		//get genre with max count 
		int max = 0; 
		String favoriteGenre = null; 
		for(String gen : genreLookupTable.keySet()) {
				if(genreLookupTable.get(gen) >max) {
					max = genreLookupTable.get(gen); 
					favoriteGenre = gen; 
				}
		}
		
		
		return favoriteGenre; 
	}
	
	public static HashMap<Integer, Double>  predictScores(UserRating user, ArrayList<UserRating> neighbors) {
		//HashMap<Integer, Double>  movies= new HashMap<Integer, Double> ();
		ArrayList<Integer> movies = new ArrayList<Integer>(); 
		HashMap<Integer, Double>  userRatings= user.getRatings();
		HashMap<Integer, Double>  moviePredictions= new HashMap<Integer, Double>(); 
		
		//Step 1: Find movies that user has not seen/rated but its neighbors have. 					
		for( UserRating other : neighbors) {
			HashMap<Integer, Double> ratings = other.getRatings(); 
			
			for(int key : ratings.keySet()) {
				if(!userRatings.containsKey(key)) {
					if(!movies.contains(key))
					movies.add(key); 
				}		
			}				
		}
		System.out.println(movies.size() +": " + neighbors.size()); 
		//Movies ArrayList now contains all the movies that the user has not seen/rated and that its neighbors have. 
		
		/*ArrayList<Integer> moviesSeen = new ArrayList<Integer>(); //This arraylist contains all the movies seen by the user to test accuracy of algorithm
		for(int movieId : userRatings.keySet()) {
			moviesSeen.add(movieId); 
		}*/
		//Step 2: take an average for each movie from how each of the neighbors rated it. 
		for(int movieId : movies) {
			double sum = 0; //for regular average
			int otherCount = 0; 
			double weightedSum = 0; //for weighted average based on the similarity score in order to improve accuracy. (Hopefully!) 
			double similaritySum = 0; 
			double weightedScore = 0; 
			for( UserRating other : neighbors) {
				HashMap<Integer, Double> ratings = other.getRatings(); 
				if(ratings.containsKey(movieId))
				{	sum+= ratings.get(movieId); 
				otherCount++; 
				weightedSum += ratings.get(movieId) * other.simscore; 
				similaritySum += other.simscore; 
				}
							
			}
			if(otherCount>0) {
			sum = sum / otherCount; 
			weightedScore = weightedSum /similaritySum; 
			}
			if(otherCount>2) {
				System.out.printf("MovieId: %d CalculatedScore: %.2f + WeightedScore: %.2f OtherCount: %d\n", movieId, sum, weightedScore, otherCount );
				moviePredictions.put(movieId, weightedScore); 
			} 
			//System.out.println("MovieId: " + movieId+ " Calculated score: "+ sum + " otherCount: " + otherCount ); 
		}
		
	
		
		
		
		return moviePredictions; 
	}
	
	public static int findClosestUser(UserRating user, ArrayList<UserRating> listOfUsers) {
		int closestUser = -1; 
		double maxScore = 0; 
		for(UserRating u : listOfUsers) {
			if( !u.equals(user)) {
			//	System.out.println(SimilarityScore(user, u) + " " + u.userName); 
				if(SimilarityScore(user, u)> maxScore) {
					maxScore = SimilarityScore(user, u); 
					closestUser = u.userId; 
				}
			}
		}
		//System.out.println(maxScore); 
		
		return closestUser; 
	}
	
	public static HashMap<Integer, Movie> readMovieFile(String file){
		File movieFile = new File(file);
		 HashMap<Integer, Movie> movies = new HashMap<Integer, Movie>(); 
		 Scanner scan = null; 
		 Movie movie = null; 
		 try {
			 scan = new Scanner(movieFile);
			 scan.nextLine(); 
			 while(scan.hasNext()) {
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
	
	public static ArrayList<UserRating> readRatingsFile(String file) {
		File ratingsFile = new File(file);
		
		Scanner scan = null; 
		ArrayList<UserRating> users = new ArrayList<UserRating>(); 
		
		try {
			 scan = new Scanner(ratingsFile);
			
			 int userCount = 0; 
			UserRating user = null; 
			 //System.out.println(scan.nextLine()); 
			 scan.nextLine(); 
			 int pastId = -1; 
			 while(scan.hasNext()) {
				 if(userCount > 10000)
					 break; 
	
				 String line = scan.nextLine(); 
				 String tokens[] = line.split(","); 
				 int userId = Integer.parseInt(tokens[0]); 
				 int movieId = Integer.parseInt(tokens[1]); 
				 double rating = Double.parseDouble(tokens[2]); 
				 
				 
				
				 if(userId != pastId)
				 {	
					 if(user!= null) 
					 {
					 users.add(user); 
					 }
				 
					 pastId = userId; 
					 System.out.println(userId + " " + userCount); 
					 user = new UserRating(userId); 	
					 userCount++; 
				 }
				 user.addRating(movieId, rating);
				 
				
				
			 }
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		return users;
		
	}
	
	
	public static ArrayList<UserRating> findNearestNeighbors(UserRating user, ArrayList<UserRating> listOfUsers, int kn) {
		HashMap<UserRating, Double> scoreRanking = new HashMap<UserRating, Double>(); 
		
		for(UserRating u : listOfUsers) {
			if( !u.equals(user)) {
				u.simscore = SimilarityScore(user, u);
				scoreRanking.put(u, u.simscore); 	
				
			//	System.out.println(SimilarityScore(user, u) + " " + u.userName); 
			}
		}
		ArrayList<UserRating> nearestNeighbors = new ArrayList<UserRating>(); 
		//scoreRanking.sort(); 
		for(int k = 0; k< kn; k++) {
		nearestNeighbors.add(Collections.max(scoreRanking.entrySet(), Map.Entry.comparingByValue()).getKey()); 
		scoreRanking.remove(Collections.max(scoreRanking.entrySet(), Map.Entry.comparingByValue()).getKey()) ; 
		}
		
		return nearestNeighbors; 	
	}
	public static double SimilarityScore(UserRating u1, UserRating u2) {
		//calculate euclidean distance between users 
		HashMap<Integer, Double> user1Ratings = u1.getRatings(); 
		HashMap<Integer, Double> user2Ratings = u2.getRatings(); 
		double distance = 0; 
		
		int nullCount = 0 ; 
		int matches = 0 ; 
			
		for(int key : user1Ratings.keySet()) { 
		
		if(user2Ratings.get(key)!= null && user1Ratings.get(key)!= null) {
		distance +=  Math.pow((user1Ratings.get(key) - user2Ratings.get(key) ), 2);
		matches++; 
		}
		else {
			nullCount++; 
		}
		}
		
		distance = Math.sqrt(distance); 
		//distance contains the euclidean distance between the two users
		double similarityScore = 1/ (1+ distance); 
		if(matches<10)
			similarityScore = 	0; 
			
		/*similarityScore is the distance inverted such that a score closer to 1 means users are similar, 
		 * and a score close to 0 means they are different 
		 * (A score of 1 means the two users are identical) 
		 */
		return similarityScore; 
	}
}
