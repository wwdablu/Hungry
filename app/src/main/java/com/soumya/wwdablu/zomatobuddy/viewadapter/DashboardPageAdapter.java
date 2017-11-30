package com.soumya.wwdablu.zomatobuddy.viewadapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.soumya.wwdablu.zomatobuddy.common.SearchTypes;
import com.soumya.wwdablu.zomatobuddy.fragment.CategoryList;
import com.soumya.wwdablu.zomatobuddy.fragment.FavouriteList;
import com.soumya.wwdablu.zomatobuddy.fragment.IRestaurantAction;
import com.soumya.wwdablu.zomatobuddy.model.LocationCoordinates;

import org.parceler.Parcels;

import java.util.ArrayList;

public class DashboardPageAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_INDEX_FAVOURITE = 3;

    public static class PageInfo {

        private String headerTitle;
        private String headerSubTitle;
        private @SearchTypes.SearchType String searchType;

        public PageInfo(String headerTitle, String headerSubTitle, @SearchTypes.SearchType String searchType) {
            this.headerTitle = headerTitle;
            this.headerSubTitle = headerSubTitle;
            this.searchType = searchType;
        }

        String getHeaderTitle() {
            return headerTitle;
        }

        String getHeaderSubTitle() {
            return headerSubTitle;
        }

        String getSearchType() {
            return searchType;
        }
    }

    private int pageCount;
    private LocationCoordinates locationCoordinates;
    private ArrayList<PageInfo> pageInfoList;
    private IRestaurantAction restaurantActionImpl;

    public DashboardPageAdapter(FragmentManager fm, LocationCoordinates locationCoordinates,
                                int pageCount, ArrayList<PageInfo> pageInfoList,
                                IRestaurantAction action) {

        super(fm);
        this.locationCoordinates = locationCoordinates;
        this.pageCount = pageCount;
        this.pageInfoList = pageInfoList;
        this.restaurantActionImpl = action;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putString(CategoryList.KEY_HEADER_TITLE, pageInfoList.get(position).getHeaderTitle());
        bundle.putString(CategoryList.KEY_HEADER_SUB_TITLE, pageInfoList.get(position).getHeaderSubTitle());
        bundle.putParcelable(CategoryList.KEY_LOCATION_COORDINATE, Parcels.wrap(locationCoordinates));
        bundle.putString(CategoryList.KEY_SEARCH_TYPE, pageInfoList.get(position).getSearchType());

        //If this is for favourite fragment
        if(SearchTypes.SEARCH_FAVOURITE.contentEquals(pageInfoList.get(position).getSearchType())) {

            FavouriteList fragment = new FavouriteList();
            fragment.setRestaurantAction(restaurantActionImpl);
            fragment.setArguments(bundle);

            return fragment;
        }

        CategoryList fragment = new CategoryList();
        fragment.setRestaurantAction(restaurantActionImpl);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
