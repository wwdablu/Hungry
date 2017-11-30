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
import com.soumya.wwdablu.zomatobuddy.databinding.FragmentFavlistBinding;
import com.soumya.wwdablu.zomatobuddy.model.search.Restaurant;
import com.soumya.wwdablu.zomatobuddy.viewadapter.FavouritesRecyclerAdapter;
import com.soumya.wwdablu.zomatobuddy.viewmodel.FavouritesViewModel;

public class FavouriteList extends Fragment implements IRestaurantAction {

    public static final String KEY_HEADER_TITLE = "headerTitle";
    public static final String KEY_HEADER_SUB_TITLE = "headerSubTitle";

    //This will always be favourite and is not dynamic
    private static final @SearchTypes.SearchType String KEY_SEARCH_TYPE = SearchTypes.SEARCH_FAVOURITE;

    private FragmentFavlistBinding binder;
    private FavouritesRecyclerAdapter adapter;
    private IRestaurantAction favRestaurantImpl;

    private FavouritesViewModel favouritesViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        favouritesViewModel = new FavouritesViewModel();

        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_favlist,
                container, false);

        adapter = new FavouritesRecyclerAdapter();
        binder.rvFavRestaurants.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binder.rvFavRestaurants.addItemDecoration(
                new FavouritesRecyclerAdapter.CardDecorator(getActivity(), R.dimen.card_margins));

        //Set the view model for the view binding
        binder.setFavViewModel(favouritesViewModel);

        //Set the header titles
        favouritesViewModel.setHeaderTitle(getArguments().getString(KEY_HEADER_TITLE));
        favouritesViewModel.setHeaderSubTitle(getArguments().getString(KEY_HEADER_SUB_TITLE));

        return binder.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binder.rvFavRestaurants.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the view model for the adapter
        adapter.setFavouriteDataList(favouritesViewModel);
        adapter.setRestaurantAction(this);
    }

    @Override
    public void onClick(Restaurant restaurant) {

        if(null == favRestaurantImpl) {
            return;
        }

        favRestaurantImpl.onClick(restaurant);
    }

    public void setRestaurantAction(IRestaurantAction action) {
        this.favRestaurantImpl = action;
    }
}
