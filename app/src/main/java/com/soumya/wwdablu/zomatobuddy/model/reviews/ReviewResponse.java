package com.soumya.wwdablu.zomatobuddy.model.reviews;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ReviewResponse {

	@SerializedName("reviews_start")
	private int reviewsStart;

	@SerializedName("user_reviews")
	private List<UserReviewsItem> userReviews;

	@SerializedName("reviews_shown")
	private int reviewsShown;

	@SerializedName("reviews_count")
	private int reviewsCount;

	public void setReviewsStart(int reviewsStart){
		this.reviewsStart = reviewsStart;
	}

	public int getReviewsStart(){
		return reviewsStart;
	}

	public void setUserReviews(List<UserReviewsItem> userReviews){
		this.userReviews = userReviews;
	}

	public List<UserReviewsItem> getUserReviews(){
		return userReviews;
	}

	public void setReviewsShown(int reviewsShown){
		this.reviewsShown = reviewsShown;
	}

	public int getReviewsShown(){
		return reviewsShown;
	}

	public void setReviewsCount(int reviewsCount){
		this.reviewsCount = reviewsCount;
	}

	public int getReviewsCount(){
		return reviewsCount;
	}

	@Override
 	public String toString(){
		return 
			"ReviewResponse{" +
			"reviews_start = '" + reviewsStart + '\'' + 
			",user_reviews = '" + userReviews + '\'' + 
			",reviews_shown = '" + reviewsShown + '\'' + 
			",reviews_count = '" + reviewsCount + '\'' + 
			"}";
		}
}