package com.soumya.wwdablu.zomatobuddy.model.reviews;

import java.util.List;

public class ReviewResponse{
	private int reviewsStart;
	private List<UserReviewsItem> userReviews;
	private String respondToReviewsViaZomatoDashboard;
	private int reviewsShown;
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

	public void setRespondToReviewsViaZomatoDashboard(String respondToReviewsViaZomatoDashboard){
		this.respondToReviewsViaZomatoDashboard = respondToReviewsViaZomatoDashboard;
	}

	public String getRespondToReviewsViaZomatoDashboard(){
		return respondToReviewsViaZomatoDashboard;
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
}