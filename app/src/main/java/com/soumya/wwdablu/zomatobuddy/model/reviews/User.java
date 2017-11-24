package com.soumya.wwdablu.zomatobuddy.model.reviews;

public class User{
	private String profileDeeplink;
	private String profileImage;
	private String profileUrl;
	private String foodieColor;
	private String name;
	private int foodieLevelNum;
	private String foodieLevel;

	public void setProfileDeeplink(String profileDeeplink){
		this.profileDeeplink = profileDeeplink;
	}

	public String getProfileDeeplink(){
		return profileDeeplink;
	}

	public void setProfileImage(String profileImage){
		this.profileImage = profileImage;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public void setProfileUrl(String profileUrl){
		this.profileUrl = profileUrl;
	}

	public String getProfileUrl(){
		return profileUrl;
	}

	public void setFoodieColor(String foodieColor){
		this.foodieColor = foodieColor;
	}

	public String getFoodieColor(){
		return foodieColor;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFoodieLevelNum(int foodieLevelNum){
		this.foodieLevelNum = foodieLevelNum;
	}

	public int getFoodieLevelNum(){
		return foodieLevelNum;
	}

	public void setFoodieLevel(String foodieLevel){
		this.foodieLevel = foodieLevel;
	}

	public String getFoodieLevel(){
		return foodieLevel;
	}
}
