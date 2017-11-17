package com.soumya.wwdablu.zomatobuddy.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.ImageView;

import com.soumya.wwdablu.zomatobuddy.R;
import com.squareup.picasso.Picasso;


public class CategoryCardViewModel extends BaseObservable {

    private ObservableField<String> name;
    private ObservableField<String> location;
    private ObservableField<String> cuisine;
    private ObservableField<String> featureImage;

    public CategoryCardViewModel() {
        super();

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

    @BindingAdapter("featureImage")
    public static void featureImage(ImageView imageView, String url) {

        //No need to handle if empty or null
        if(TextUtils.isEmpty(url)) {
            return;
        }

        Picasso.with(imageView.getContext())
            .load(url)
            .placeholder(R.drawable.default_card_bg)
            .error(R.drawable.default_card_bg)
            .into(imageView);
    }
}
