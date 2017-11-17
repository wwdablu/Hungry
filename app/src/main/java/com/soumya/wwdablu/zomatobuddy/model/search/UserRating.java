package com.soumya.wwdablu.zomatobuddy.model.search;

public class UserRating{
	private String aggregateRating;
	private String ratingColor;
	private String ratingText;
	private String votes;

	public void setAggregateRating(String aggregateRating){
		this.aggregateRating = aggregateRating;
	}

	public String getAggregateRating(){
		return aggregateRating;
	}

	public void setRatingColor(String ratingColor){
		this.ratingColor = ratingColor;
	}

	public String getRatingColor(){
		return ratingColor;
	}

	public void setRatingText(String ratingText){
		this.ratingText = ratingText;
	}

	public String getRatingText(){
		return ratingText;
	}

	public void setVotes(String votes){
		this.votes = votes;
	}

	public String getVotes(){
		return votes;
	}
}
