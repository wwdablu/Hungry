package com.soumya.wwdablu.zomatobuddy.model.reviews;

public class Review{
	private String ratingColor;
	private String reviewTimeFriendly;
	private String ratingText;
	private int commentsCount;
	private int rating;
	private String reviewText;
	private String id;
	private User user;
	private int timestamp;
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

	public void setRating(int rating){
		this.rating = rating;
	}

	public int getRating(){
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
}
