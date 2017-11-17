package com.soumya.wwdablu.zomatobuddy.model.search;

import java.util.List;

public class SearchResponse{
	private int resultsFound;
	private int resultsShown;
	private List<RestaurantsItem> restaurants;
	private int resultsStart;

	public void setResultsFound(int resultsFound){
		this.resultsFound = resultsFound;
	}

	public int getResultsFound(){
		return resultsFound;
	}

	public void setResultsShown(int resultsShown){
		this.resultsShown = resultsShown;
	}

	public int getResultsShown(){
		return resultsShown;
	}

	public void setRestaurants(List<RestaurantsItem> restaurants){
		this.restaurants = restaurants;
	}

	public List<RestaurantsItem> getRestaurants(){
		return restaurants;
	}

	public void setResultsStart(int resultsStart){
		this.resultsStart = resultsStart;
	}

	public int getResultsStart(){
		return resultsStart;
	}
}