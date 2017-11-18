package com.soumya.wwdablu.zomatobuddy.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.common.SearchTypes;
import com.soumya.wwdablu.zomatobuddy.databinding.FragmentCategorylistBinding;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;
import com.soumya.wwdablu.zomatobuddy.viewadapter.CategoryRecyclerAdapter;
import com.soumya.wwdablu.zomatobuddy.viewmodel.CategoryViewModel;

import org.parceler.Parcels;

public class CategoryList extends Fragment implements CategoryViewModel.ISearchResult {

    public static final String KEY_HEADER_TITLE = "headerTitle";
    public static final String KEY_HEADER_SUB_TITLE = "headerSubTitle";
    public static final String KEY_LOCATION_COORDINATE = "locationCoordinate";
    public static final @SearchTypes.SearchType String KEY_SEARCH_TYPE = "searchType";

    private FragmentCategorylistBinding categorylistBinder;
    private CategoryRecyclerAdapter categoryRecyclerAdapter;

    private CategoryViewModel categoryViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        categoryViewModel = new CategoryViewModel(getArguments().getString(KEY_SEARCH_TYPE),
                Parcels.unwrap(getArguments().getParcelable(KEY_LOCATION_COORDINATE)), this);

        categorylistBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_categorylist,
                container, false);

        //Set the header titles
        categoryViewModel.setHeaderTitle(getArguments().getString(KEY_HEADER_TITLE));
        categoryViewModel.setHeaderSubTitle(getArguments().getString(KEY_HEADER_SUB_TITLE));

        //Setup the recycler view
        categoryRecyclerAdapter = new CategoryRecyclerAdapter();
        categorylistBinder.rvCategoryRestaurants.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        categorylistBinder.rvCategoryRestaurants.addItemDecoration(
                new CategoryRecyclerAdapter.CardDecorator(getActivity(), R.dimen.card_margins));
        categorylistBinder.rvCategoryRestaurants.setAdapter(categoryRecyclerAdapter);

        return categorylistBinder.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSearchComplete(SearchResponse searchResponse) {
        categorylistBinder.setCatViewModel(categoryViewModel);
        categoryRecyclerAdapter.setSearchResponse(searchResponse);
    }
}
