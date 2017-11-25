package com.soumya.wwdablu.zomatobuddy.model.restaurant;

import com.google.gson.annotations.SerializedName;

public class UserRating{

	@SerializedName("aggregate_rating")
	private String aggregateRating;

	@SerializedName("rating_color")
	private String ratingColor;

	@SerializedName("rating_text")
	private String ratingText;

	@SerializedName("votes")
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

	@Override
 	public String toString(){
		return 
			"UserRating{" + 
			"aggregate_rating = '" + aggregateRating + '\'' + 
			",rating_color = '" + ratingColor + '\'' + 
			",rating_text = '" + ratingText + '\'' + 
			",votes = '" + votes + '\'' + 
			"}";
		}
}