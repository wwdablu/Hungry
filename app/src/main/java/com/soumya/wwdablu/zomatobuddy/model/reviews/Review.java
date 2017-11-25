package com.soumya.wwdablu.zomatobuddy.model.reviews;

import com.google.gson.annotations.SerializedName;


public class Review{

	@SerializedName("rating_color")
	private String ratingColor;

	@SerializedName("review_time_friendly")
	private String reviewTimeFriendly;

	@SerializedName("rating_text")
	private String ratingText;

	@SerializedName("comments_count")
	private int commentsCount;

	@SerializedName("rating")
	private double rating;

	@SerializedName("review_text")
	private String reviewText;

	@SerializedName("id")
	private String id;

	@SerializedName("user")
	private User user;

	@SerializedName("timestamp")
	private int timestamp;

	@SerializedName("likes")
	private int likes;

	public void setRatingColor(String ratingColor){
		this.ratingColor = ratingColor;
	}

	public String getRatingColor(){
		return ratingColor;
	}

	public void setReviewTimeFriendly(String reviewTimeFriendly){
		this.reviewTimeFriendly = reviewTimeFriendly;
	}

	public String getReviewTimeFriendly(){
		return reviewTimeFriendly;
	}

	public void setRatingText(String ratingText){
		this.ratingText = ratingText;
	}

	public String getRatingText(){
		return ratingText;
	}

	public void setCommentsCount(int commentsCount){
		this.commentsCount = commentsCount;
	}

	public int getCommentsCount(){
		return commentsCount;
	}

	public void setRating(double rating){
		this.rating = rating;
	}

	public double getRating(){
		return rating;
	}

	public void setReviewText(String reviewText){
		this.reviewText = reviewText;
	}

	public String getReviewText(){
		return reviewText;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setTimestamp(int timestamp){
		this.timestamp = timestamp;
	}

	public int getTimestamp(){
		return timestamp;
	}

	public void setLikes(int likes){
		this.likes = likes;
	}

	public int getLikes(){
		return likes;
	}

	@Override
 	public String toString(){
		return 
			"Review{" + 
			"rating_color = '" + ratingColor + '\'' + 
			",review_time_friendly = '" + reviewTimeFriendly + '\'' + 
			",rating_text = '" + ratingText + '\'' + 
			",comments_count = '" + commentsCount + '\'' + 
			",rating = '" + rating + '\'' + 
			",review_text = '" + reviewText + '\'' + 
			",id = '" + id + '\'' + 
			",user = '" + user + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			",likes = '" + likes + '\'' + 
			"}";
		}
}