package com.soumya.wwdablu.zomatobuddy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.fragment.Search;
import com.soumya.wwdablu.zomatobuddy.model.search.Restaurant;

import timber.log.Timber;

public class SearchActivity extends AppCompatActivity implements Search.ISearchAcion {

    private static final String FTAG_SEARCH_FRAGMENT = "fragmentSearch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Search fragment = new Search();
        fragment.setSearchAction(this);

        Bundle bundle = new Bundle();
        bundle.putParcelable("location", getIntent().getParcelableExtra("location"));
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
            .add(R.id.fl_search_activity, fragment, FTAG_SEARCH_FRAGMENT)
            .commit();
    }

    @Override
    public void onRestaurantSelected(Restaurant restaurant) {

        Timber.d(restaurant.getName());
        Intent launchIntent = new Intent(this, RestaurantDetailsActivity.class);
        launchIntent.putExtra("resid", restaurant.getId());
        launchIntent.putExtra("rName", restaurant.getName());
        startActivity(launchIntent);
    }
}
