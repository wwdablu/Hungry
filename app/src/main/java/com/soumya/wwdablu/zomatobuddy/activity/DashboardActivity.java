package com.soumya.wwdablu.zomatobuddy.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.common.Analytics;
import com.soumya.wwdablu.zomatobuddy.common.SearchTypes;
import com.soumya.wwdablu.zomatobuddy.database.CacheDB;
import com.soumya.wwdablu.zomatobuddy.databinding.ActivityDashboardBinding;
import com.soumya.wwdablu.zomatobuddy.fragment.CategoryList;
import com.soumya.wwdablu.zomatobuddy.fragment.IRestaurantAction;
import com.soumya.wwdablu.zomatobuddy.model.LocationCoordinates;
import com.soumya.wwdablu.zomatobuddy.model.categories.CategoryResponse;
import com.soumya.wwdablu.zomatobuddy.model.search.Restaurant;
import com.soumya.wwdablu.zomatobuddy.viewadapter.DashboardPageAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import timber.log.Timber;

public class DashboardActivity extends AppCompatActivity implements IRestaurantAction {

    private static final int MAX_TABS = 4;

    private ActivityDashboardBinding dashboardBinder;
    private LocationCoordinates locationCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dashboardBinder = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        //Fetch the data received from the splash screen
        locationCoordinates = Parcels.unwrap(getIntent().getBundleExtra("data").getParcelable("location"));
        CategoryResponse categoryResponse = Parcels.unwrap(getIntent().getBundleExtra("data").getParcelable("categoryResponse"));

        //Set the toolbar
        setSupportActionBar(dashboardBinder.toolbar);

        //Create the state pager adapter
        FragmentStatePagerAdapter fragmentStatePagerAdapter = new DashboardPageAdapter(getSupportFragmentManager(),
                locationCoordinates, MAX_TABS, getTabHeaderInfos(), this);

        // Set up the ViewPager with the state pager adapter.
        dashboardBinder.container.setAdapter(fragmentStatePagerAdapter);

        dashboardBinder.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(dashboardBinder.tabs));
        dashboardBinder.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(dashboardBinder.container));

        dashboardBinder.tabs.setSelectedTabIndicatorColor(Color.YELLOW);

        //Analytics information
        Analytics.setAnalyiticsFor("DemoUser");
        Analytics.setCurrentScreen(this, "Dashboard");
        Analytics.setUserAction(Analytics.EVENT_APP_START, Analytics.PARAM_APP_START, "DemoUser");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.menu_source_search:
                launchSearchActivity();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        CacheDB.getInstance().purgeCache();
        super.onBackPressed();
    }

    @Override
    public void onClick(Restaurant restaurant) {

        launchNextActivity(restaurant);
    }

    private void launchNextActivity(Restaurant restaurant) {
        Timber.d(restaurant.getName());
        Intent launchIntent = new Intent(this, RestaurantDetailsActivity.class);
        launchIntent.putExtra("resid", restaurant.getId());
        launchIntent.putExtra("rName", restaurant.getName());
        launchIntent.putExtra("rCuisine", restaurant.getCuisines());
        startActivity(launchIntent);
    }

    private void launchSearchActivity() {

        Timber.d("Launching search activity");
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchIntent.putExtra("location", Parcels.wrap(locationCoordinates));
        startActivity(searchIntent);
    }

    private ArrayList<DashboardPageAdapter.PageInfo> getTabHeaderInfos() {

        ArrayList<DashboardPageAdapter.PageInfo> pageInfos = new ArrayList<>(MAX_TABS);

        pageInfos.add(new DashboardPageAdapter.PageInfo(
                "Go out for lunch or dinner",
                "Dine-out restaurants",
                SearchTypes.SEARCH_DINE_OUT
        ));

        pageInfos.add(new DashboardPageAdapter.PageInfo(
                "Get food delivered",
                "Delivery restaurants",
                SearchTypes.SEARCH_DELIVERY
        ));

        pageInfos.add(new DashboardPageAdapter.PageInfo(
                "Grab food to-go",
                "Delivery restaurants",
                SearchTypes.SEARCH_TAKE_AWAY
        ));

        pageInfos.add(new DashboardPageAdapter.PageInfo(
                "Favourites",
                "Favourite restaurants",
                SearchTypes.SEARCH_FAVOURITE
        ));

        return pageInfos;
    }
}
