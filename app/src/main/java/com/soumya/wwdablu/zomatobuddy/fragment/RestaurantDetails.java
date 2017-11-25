package com.soumya.wwdablu.zomatobuddy.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.databinding.FragmentRestaurantDetailsBinding;
import com.soumya.wwdablu.zomatobuddy.viewadapter.ItemsAdapter;
import com.soumya.wwdablu.zomatobuddy.viewadapter.ReviewAdapter;
import com.soumya.wwdablu.zomatobuddy.viewmodel.RestaurantDetailsViewModel;

import java.util.ArrayList;

public class RestaurantDetails extends Fragment implements RestaurantDetailsViewModel.IDetailsAction,
                ItemsAdapter.IIteamAction {

    private FragmentRestaurantDetailsBinding binder;
    private RestaurantDetailsViewModel restaurantDetailsViewModel;
    private ItemsAdapter adapter;
    private ReviewAdapter reviewAdapter;

    private String restaurantName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_details,
                container, false);

        //Get the name of the restaurant from the caller
        restaurantName = getArguments().getString("rName");

        restaurantDetailsViewModel = new RestaurantDetailsViewModel(this);
        binder.cardLayoutRestDetailsHeader.setRestDetails(restaurantDetailsViewModel);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binder.toolbarRestDetails);

        binder.ablRestDetails.addOnOffsetChangedListener(offsetChangeListener);

        //Restaurant Details
        binder.rvRestDetailsActions.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        binder.rvRestDetailsActions.addItemDecoration(new ItemsAdapter.ItemDecorate(getActivity(), R.dimen.card_margins));

        adapter = new ItemsAdapter(restaurantDetailsViewModel, this);
        binder.rvRestDetailsActions.setAdapter(adapter);

        //Restaurant Reviews
        binder.rvReviewList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binder.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchRestaurantDetails();
    }

    @Override
    public void onPause() {
        super.onPause();
        restaurantDetailsViewModel.clean();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reviewAdapter.clean();
    }

    @Override
    public void onSuccess() {

        //Display the information
        adapter.bindData(getItems());

        binder.mergePb.pbRestDetailsProgress.setVisibility(View.GONE);
        binder.ablRestDetails.setVisibility(View.VISIBLE);
        binder.nsvRestDetailsContainer.setVisibility(View.VISIBLE);

        //Now get and display the review information
        reviewAdapter = new ReviewAdapter(getArguments().getString("resid"));
        binder.rvReviewList.setAdapter(reviewAdapter);
    }

    @Override
    public void onError() {
        handleErrorResponse();
    }

    @Override
    public void onClick(int position) {

        Intent viewIntent = new Intent(Intent.ACTION_VIEW);

        switch(position) {
            case 0:
                viewIntent.setData(Uri.parse(restaurantDetailsViewModel.getMenuUrl()));
                break;

            case 1:
                viewIntent.setData(Uri.parse(restaurantDetailsViewModel.getPhotoUrl()));
                break;

            case 2:
                viewIntent.setData(Uri.parse(restaurantDetailsViewModel.getWebsiteUrl()));
                break;

            case 3:
                viewIntent.setData(Uri.parse(restaurantDetailsViewModel.getZomatoDeepLink()));
                break;
        }

        //Resolve and launch the intent
        if(null != viewIntent.resolveActivity(getActivity().getPackageManager())) {
            startActivity(viewIntent);
        }
    }

    private AppBarLayout.OnOffsetChangedListener offsetChangeListener = new AppBarLayout.OnOffsetChangedListener() {

        boolean isShow = false;
        int scrollRange = -1;
        int visibleRange = 0;

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            if (scrollRange == -1) {
                scrollRange = appBarLayout.getTotalScrollRange();
                visibleRange = scrollRange / 4;
            }

            if(scrollRange + verticalOffset <= visibleRange && !isShow) {
                binder.ctRestDetails.setTitle(restaurantName);
                isShow = true;
            } else if(scrollRange + verticalOffset > visibleRange && isShow) {
                binder.ctRestDetails.setTitle(" ");
                isShow = false;
            }
        }
    };

    private void fetchRestaurantDetails() {

        String s = getArguments().getString("resid");
        restaurantDetailsViewModel.getRestaurantDetails(s);
    }

    private void handleErrorResponse() {

        Toast.makeText(getActivity(), R.string.could_not_fetch_generic, Toast.LENGTH_SHORT)
                .show();
    }

    private ArrayList<ItemsAdapter.ItemInfo> getItems() {

        ArrayList<ItemsAdapter.ItemInfo> arrayList = new ArrayList<>(5);
        arrayList.add(new ItemsAdapter.ItemInfo("Menu", R.drawable.restaurant_menu));
        arrayList.add(new ItemsAdapter.ItemInfo("Photo", R.drawable.food_icon));
        arrayList.add(new ItemsAdapter.ItemInfo("Website", R.drawable.web_icon));
        arrayList.add(new ItemsAdapter.ItemInfo("Zomato App", R.drawable.link_icon));

        return arrayList;
    }
}
