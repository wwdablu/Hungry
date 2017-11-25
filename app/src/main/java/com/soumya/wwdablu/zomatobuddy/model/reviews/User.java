package com.soumya.wwdablu.zomatobuddy.model.reviews;

import com.google.gson.annotations.SerializedName;


public class User{

	@SerializedName("profile_deeplink")
	private String profileDeeplink;

	@SerializedName("profile_image")
	private String profileImage;

	@SerializedName("profile_url")
	private String profileUrl;

	@SerializedName("foodie_color")
	private String foodieColor;

	@SerializedName("name")
	private String name;

	@SerializedName("foodie_level_num")
	private int foodieLevelNum;

	@SerializedName("foodie_level")
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

	@Override
 	public String toString(){
		return 
			"User{" + 
			"profile_deeplink = '" + profileDeeplink + '\'' + 
			",profile_image = '" + profileImage + '\'' + 
			",profile_url = '" + profileUrl + '\'' + 
			",foodie_color = '" + foodieColor + '\'' + 
			",name = '" + name + '\'' + 
			",foodie_level_num = '" + foodieLevelNum + '\'' + 
			",foodie_level = '" + foodieLevel + '\'' + 
			"}";
		}
}