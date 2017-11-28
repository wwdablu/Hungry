package com.soumya.wwdablu.zomatobuddy.common;

import android.support.annotation.DrawableRes;

import com.soumya.wwdablu.zomatobuddy.R;

import java.util.HashMap;

public class DefaultCuisineImage {

    private static HashMap<String, Integer> cuisineDefaultCardImage;
    static {
        initCuisineDefaultCardImage();
    }

    public static synchronized @DrawableRes int getCuisineImage(String cuisine) {

        @DrawableRes int drawableRes;

        if(cuisineDefaultCardImage.containsKey(cuisine.toLowerCase())) {
            drawableRes = cuisineDefaultCardImage.get(cuisine.toLowerCase());
        } else {
            drawableRes = cuisineDefaultCardImage.get("default");
        }

        return drawableRes;
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
