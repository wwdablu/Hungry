package com.soumya.wwdablu.zomatobuddy.model.restaurant;

import com.google.gson.annotations.SerializedName;


public class R{

	@SerializedName("res_id")
	private int resId;

	public void setResId(int resId){
		this.resId = resId;
	}

	public int getResId(){
		return resId;
	}

	@Override
 	public String toString(){
		return 
			"R{" + 
			"res_id = '" + resId + '\'' + 
			"}";
		}
}