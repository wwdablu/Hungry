package com.soumya.wwdablu.zomatobuddy.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.common.SearchTypes;
import com.soumya.wwdablu.zomatobuddy.database.CacheDB;
import com.soumya.wwdablu.zomatobuddy.databinding.ActivityDashboardBinding;
import com.soumya.wwdablu.zomatobuddy.model.LocationCoordinates;
import com.soumya.wwdablu.zomatobuddy.model.categories.CategoryResponse;
import com.soumya.wwdablu.zomatobuddy.viewadapter.DashboardPageAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private static final int MAX_TABS = 3;

    private LocationCoordinates locationCoordinates;
    private CategoryResponse categoryResponse;

    private FragmentStatePagerAdapter fragmentStatePagerAdapter;
    private ActivityDashboardBinding dashboardBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinder = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);


        //Fetch the data received from the splash screen
        locationCoordinates = Parcels.unwrap(getIntent().getBundleExtra("data").getParcelable("location"));
        categoryResponse = Parcels.unwrap(getIntent().getBundleExtra("data").getParcelable("categoryResponse"));

        //Set the toolbar
        setSupportActionBar(dashboardBinder.toolbar);

        //Create the state pager adapter
        fragmentStatePagerAdapter = new DashboardPageAdapter(getSupportFragmentManager(),
                locationCoordinates, MAX_TABS, getTabHeaderInfos());

        // Set up the ViewPager with the state pager adapter.
        dashboardBinder.container.setAdapter(fragmentStatePagerAdapter);

        dashboardBinder.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(dashboardBinder.tabs));
        dashboardBinder.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(dashboardBinder.container));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        CacheDB.getInstance().purgeCache();
        super.onBackPressed();
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

        return pageInfos;
    }
}
