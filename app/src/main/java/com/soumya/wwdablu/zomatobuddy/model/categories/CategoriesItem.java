package com.soumya.wwdablu.zomatobuddy.model.categories;

import org.parceler.Parcel;

@Parcel
public class CategoriesItem{

	public Categories categories;

	public void setCategories(Categories categories){
		this.categories = categories;
	}

	public Categories getCategories(){
		return categories;
	}
}
