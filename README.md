# KNN-Based Clustering for Movie Recommender Systems

A movie recommendation system implementation which uses kNN-collaborative filtering.  
## kNN Algorithm and Implementation
K-Nearest Neighbors algorithm is used for clustering data into similar groups/clusters. (Neighbors) 
This implementation of kNN uses a similarity score which rates how similar two users are to each other based on the movies they have both rated. It uses simple Euclidean distance to calculate the similarity score. (This calculation could be  improved by implementing Pearson correlation coefficient or cosine similarity instead.) 
Given a new user, the algorithm will calculate the similarity score between it and all of the other users. It will then find the K most similar users and based on the movies that these K-users have rated, it will predict how the new user would rate movies that it has not yet seen/rated. 
More info on kNN: https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm
### Classes 
#### Movie.java: Object represents a specific movie from data set. 
-Includes Movie Id, Title, Genre, and a Predicted Score (for a particular user) 
#### UserRating.java: Object represents a specific user from the data set and their movie rankings.  
-Includes User ID, a HashMap which contains a key value pair of Movie Ids and ratings and functions for updating and adding ratings. 
#### MovieRecommender.java: This class contains all the functions(methods) required for the movie recommender and kNN implementation. 
### Data Set
This implementation works with MovieLens 20M Data Set https://grouplens.org/datasets/movielens/20m/
The datasets describe ratings and free-text tagging activities from MovieLens, a movie recommendation service. It contains 20000263 ratings and 465564 tag applications across 27278 movies. These data were created by 138493 users between January 09, 1995 and March 31, 2015. This dataset was generated on October 17, 2016. Users were selected at random for inclusion. All selected users had rated at least 20 movies.
#### movie.csv: movieId, title, genres
#### rating.csv: userId, movieId, rating, timestamp

### Demo Run (Walkthrough)

1. Initialize MovieRecommender object, passing in data files as a parameters. 

```
MovieRecommender recommender = new MovieRecommender("rating.csv", "movie.csv"); 
```
2. Create a user object and give it movie ratings.  
```
UserRating newuser = new UserRating(0); 
		newuser.addRating(858, 5); //The Godfather was rated 5 stars
		newuser.addRating(1028, 3); //Mary Poppins was rated 3 stars
		newuser.addRating(1721, 4); //Titanic was rated 4 stars. 
		newuser.addRating(7064, 5); //Beauty and the beast was rated 5 stars 
		newuser.addRating(1172, 5);//Cinema Paradiso was rated 5 stars. 
		newuser.addRating(2324, 5); //La Vita e bella was rated 5 stars
		newuser.addRating(527, 4); //Schindler's list was rated 4 stars
		newuser.addRating(64034 ,5);//Boy in the Striped Pyjamas was rated 5 stars
		newuser.addRating(60397 ,4); //Mamma Mia was rated 4 stars
		newuser.addRating(3545 ,4);//Cabaret was rated 4 stars
		newuser.addRating(4681 ,4); //War of the roses was rated 4 stars
		newuser.addRating(597, 4); //Pretty woman was rated 4 stars
		newuser.addRating(1955, 4); //Kramer vs Kramer was rated 4 stars.
		newuser.addRating(4857, 4);//Fiddler on the roof was rated 4 stars
		newuser.addRating(45720, 4);//Devil wears prada was rated 4 stars
		newuser.addRating(58, 5); //Postino II was rated 4 stars 
		newuser.addRating(1206, 1); //A Clockwork Orange was rated 1 star
		newuser.addRating(33836, 1); //bewitched was rated 1 star
		newuser.addRating(2315, 1); // Bride of chucky was rated 1 star
		newuser.addRating(5323, 1); // Jason X was rated 1 star
		newuser.addRating(7004, 1); //Kindergarten Cop was rated 1 star
```
3. Pass in this user object to the recommendMovies() method. It returns an ArrayList of Movies which will be recommended to user. (Each movie object has a predicted score class variable which contains the algorithms score prediction for this user) 
```
ArrayList<Movie> RecommendedMovies = recommender.recommendMovies(newuser); 	
```
4. Do something with movie data. (Print out etc.) 
Example: 
```
String fiveStars = "*****"; 
		String fourStars = "****"; 
		String threeStars = "***";
		String twoStars = "**"; 
		String oneStar = "*"; 
		System.out.println("The following movies and scores were predicted for you:  "); 
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
```
5. Final Results Example: (With some movies omited)  
```
The following movies and scores were predicted for you: 
"Into the Arms of Strangers: Stories of the Kindertransport (2000)"  *****
"Central Station (Central do Brasil) (1998)"  *****
"Lone Star (1996)"  *****
"Sting  *****
"Gosford Park (2001)"  *****
"War Room  *****
"Madness of King George  *****
"When We Were Kings (1996)"  *****
"Casablanca (1942)"  *****
"Life and Times of Hank Greenberg  *****
"Beauty of the Day (Belle de jour) (1967)"  *****
"8 1/2 (8½) (1963)"  *****
"Brassed Off (1996)"  *****
"In the Heat of the Night (1967)"  *****
"Splendor in the Grass (1961)"  *****
"Splash (1984)"  *****
"Maltese Falcon  *****
"Untouchables  *****
"Silence of the Lambs  *****
"Amores Perros (Love's a Bitch) (2000)"  *****
"Insider  *****
"Apartment  *****
"Babette's Feast (Babettes gæstebud) (1987)"  *****
"Night Shift (1982)"  *****
"Inception (2010)" ****
"All the King's Men (1949)" ****
"JFK (1991)" ****
"Monsters ****
"American Graffiti (1973)" ****
"Mummy ***
"Scary Movie 2 (2001)" **
"Inspector Gadget (1999)" **
"Psycho (1998)" **
"Blade II (2002)" **
"Tommy (1975)" **
"Days of Thunder (1990)" **
"Mouse Hunt (1997)" **
"25th Hour (2002)" **
"European Vacation (aka National Lampoon's European Vacation) (1985)" **
"Robin Hood: Men in Tights (1993)" **
"Naked Gun 33 1/3: The Final Insult (1994)" **
"American Tail: Fievel Goes West **
"Pope of Greenwich Village **
"Airplane II: The Sequel (1982)" **
"Tigger Movie *
"Kazaam (1996)" *
"I Know What You Did Last Summer (1997)" *
"Pokémon: The First Movie (1998)" *
"Free Willy 3: The Rescue (1997)" *
"Police Academy 4: Citizens on Patrol (1987)" *
"Amityville 3-D (1983)" *
"3 Ninjas: High Noon On Mega Mountain (1998)" *
"Police Academy 3: Back in Training (1986)" *
"Dudley Do-Right (1999)" *
```
## Testing

The method algorithmAccuracy() in the MovieRecommender class is used to test the accuracy of the algorithm. It essentially removes a known user from the data set and runs the algorithm as it if were a new user and hides some its known ratings for movies. Based on the predicted scores for this user it compares them to its actual ratings for each movie and calculates the mean absolute percent error to determine the predicted scores' accuracy. The method will perform this task for multiple users in the data set to determine how effective the algorithm is overall. 
NOTE: In 52 runs, the algorithm was 82% accurate. 

## Optimization
The following are possible optimizations for this K-NN implementation:
###### 1. Optimizing K:
K was calculated by taking the square root of the number of possible neighbors(users) and dividing by 2.<br />
K = Ceil(Sqrt(460)/2)= 11<br />
Note: Smaller K values had a much greater variation between users than larger K values tested. <br />
The following graph illustrates the varying accuracy percentages for different k values that were tested:
![alt text](https://github.com/jdsada30/MovieRecommender/blob/master/K-Value-Accuracy.png)
###### 2. Similarity Score Calculation:
This implementation uses euclidean distance to determine how similar two users are from each other. However, there are other possible implementations for determining similarity between users including cosine similarity and pearson correlation coefficient calculation which could be implemented to improve the algorithms accuracy. 






