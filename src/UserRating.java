import java.util.HashMap;

public class UserRating {

int userId; 
HashMap<Integer, Double> MovieRatingTable;
double simscore; 


public UserRating(int id , HashMap<Integer, Double> MovieRatingTable) { //new constructor
	 this.userId = id; 
	 this.MovieRatingTable = MovieRatingTable; 
}
 
 public UserRating(int id ) { //new constructor
	 this.userId = id; 
	 this.MovieRatingTable = new HashMap<Integer, Double>(); 
 }
 

 public void addRating(int movieId, double rating) {//new function
	 MovieRatingTable.put(movieId, rating);  
 }
 
 
 public HashMap<Integer, Double> getRatings(){ //new getter
	 return this.MovieRatingTable; 
 }

}
