package com.soumya.wwdablu.zomatobuddy.model.restaurant;

import com.google.gson.annotations.SerializedName;


public class Location{

	@SerializedName("zipcode")
	private String zipcode;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("locality_verbose")
	private String localityVerbose;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("locality")
	private String locality;

	@SerializedName("country_id")
	private int countryId;

	@SerializedName("city_id")
	private int cityId;

	@SerializedName("longitude")
	private String longitude;

	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setLocalityVerbose(String localityVerbose){
		this.localityVerbose = localityVerbose;
	}

	public String getLocalityVerbose(){
		return localityVerbose;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setLocality(String locality){
		this.locality = locality;
	}

	public String getLocality(){
		return locality;
	}

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
	}

	public void setCityId(int cityId){
		this.cityId = cityId;
	}

	public int getCityId(){
		return cityId;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"zipcode = '" + zipcode + '\'' + 
			",address = '" + address + '\'' + 
			",city = '" + city + '\'' + 
			",locality_verbose = '" + localityVerbose + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",locality = '" + locality + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",city_id = '" + cityId + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}