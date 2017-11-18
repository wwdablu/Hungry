package com.soumya.wwdablu.zomatobuddy.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.ImageView;

import com.soumya.wwdablu.zomatobuddy.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class CategoryCardViewModel extends BaseObservable {

    private static HashMap<String, Integer> cuisineDefaultCardImage;

    private ObservableField<String> name;
    private ObservableField<String> location;
    private ObservableField<String> cuisine;
    private ObservableField<String> featureImage;

    public CategoryCardViewModel() {

        super();
        initCuisineDefaultCardImage();

        name = new ObservableField<>();
        location = new ObservableField<>();
        cuisine = new ObservableField<>();
        featureImage = new ObservableField<>();
    }

    @Bindable
    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Bindable
    public ObservableField<String> getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    @Bindable
    public ObservableField<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine.set(cuisine);
    }

    @Bindable
    public ObservableField<String> getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage.set(featureImage);
    }

    @BindingAdapter({"featureImage", "cuisines"})
    public static void featureImage(ImageView imageView, String url, String cuisines) {

        //If URL has been provided, then load using Picasso
        if(!TextUtils.isEmpty(url)) {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .placeholder(R.drawable.default_card_bg)
                    .error(R.drawable.default_card_bg)
                    .into(imageView);

            return;
        }

        //If the URL is invalid, then we can use local images based on the cuisine
        String cuisine = cuisines.split(",")[0];
        if(cuisineDefaultCardImage.containsKey(cuisine.toLowerCase())) {
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(
                    cuisineDefaultCardImage.get(cuisine.toLowerCase())));
        } else {
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(
                    cuisineDefaultCardImage.get("default")));
        }
    }

    private static void initCuisineDefaultCardImage() {

        if(null == cuisineDefaultCardImage) {
            cuisineDefaultCardImage = new HashMap<>();
        }

        //No need to insert, already its done
        if(0 != cuisineDefaultCardImage.size()) {
            return;
        }

        cuisineDefaultCardImage.put("indian", R.drawable.default_food_indian);
        cuisineDefaultCardImage.put("mexican", R.drawable.default_food_mexican);
        cuisineDefaultCardImage.put("american", R.drawable.default_food_american);
        cuisineDefaultCardImage.put("chinese", R.drawable.default_food_chinese);
        cuisineDefaultCardImage.put("italian", R.drawable.default_food_italian);
        cuisineDefaultCardImage.put("japanese", R.drawable.default_food_japanese);
        cuisineDefaultCardImage.put("default", R.drawable.default_food);
    }
}
