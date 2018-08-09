# Movie Recommender using kNN Algorithm	

A movie recommendation system implementation which uses simple kNN-collaborative filtering.  
## kNN Algorithm and Implementation
K-Nearest Neighbors algorithm is used for clustering data into similar groups/clusters. (Neighbors) 
This implementation of kNN uses a similarity score which rates how similar two users are to each other based on the movies they have both rated. It uses simple euclidean distance to calculate the similarity score. (This calculation could be  improved by implementing Pearson correlation coefficient or cosine similarity instead.) 
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

### Walkthrough

1. Initialize MovieRecommender object, passing in data files as a parameters. 

```
MovieRecommender recommender = new MovieRecommender("rating.csv", "movie.csv"); 
```
2. Create a user object and give it movie ratings. 
```
UserRating newuser = new UserRating(0); 
		newuser.addRating(858, 5);
		newuser.addRating(1028, 3);
		newuser.addRating(1721, 4);
		newuser.addRating(7064, 5);
		newuser.addRating(1172, 5);
		newuser.addRating(2324, 5);
		newuser.addRating(527, 4);
		newuser.addRating(64034 ,5);
		newuser.addRating(60397 ,4);
		newuser.addRating(3545 ,4);
		newuser.addRating(4681 ,4);
		newuser.addRating(597, 4);
		newuser.addRating(1955, 4);
		newuser.addRating(4857, 4);
		newuser.addRating(45720, 4);
		newuser.addRating(58, 5);
		newuser.addRating(1206, 1);
		newuser.addRating(33836, 1);
		newuser.addRating(2315, 1);
		newuser.addRating(5323, 1);
		newuser.addRating(7004, 1);
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
5. Final Results Example: (With some movies ommited)  
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

The method algorithmAccuracy() in the MovieRecommender class is used to test the accuracy of the algorithm. It essentially removes a known user from the data set and runs the algorithm as it if were a new user and hides some its known ratings for movies. Based on the predicted scores for this user it compares them to its actual ratings for each movie and calculates the mean absolute precent error to determine the predicted scores' accuracy. The method will perform this task for multiple users in the data set to determine how effective the algorithm is overall. 
NOTE: In 52 runs, the algorithm was 80% accurate. 


