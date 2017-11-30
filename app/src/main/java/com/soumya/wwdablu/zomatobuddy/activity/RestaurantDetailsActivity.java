package com.soumya.wwdablu.zomatobuddy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.common.Analytics;
import com.soumya.wwdablu.zomatobuddy.fragment.RestaurantDetails;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private static final String FTAG_DETAILS = "detailsFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Bundle bundle = new Bundle();
        bundle.putString("resid", getIntent().getStringExtra("resid"));
        bundle.putString("rName", getIntent().getStringExtra("rName"));
        bundle.putString("rCuisine", getIntent().getStringExtra("rCuisine"));

        RestaurantDetails fragment = new RestaurantDetails();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_rest_details_activity_container, fragment, FTAG_DETAILS)
                .commit();

        //Analytics information
        Analytics.setCurrentScreen(this, "Restaurant Details");
        Analytics.setUserAction(Analytics.EVENT_RESTAURANT_DETAILS, Analytics.PARAM_RESTAURANT_SELECTED,
                getIntent().getStringExtra("rName"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
