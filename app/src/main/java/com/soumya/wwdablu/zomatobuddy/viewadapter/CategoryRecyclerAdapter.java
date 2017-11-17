package com.soumya.wwdablu.zomatobuddy.viewadapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.databinding.CardRestaurantInfoBinding;
import com.soumya.wwdablu.zomatobuddy.model.search.Restaurant;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;
import com.soumya.wwdablu.zomatobuddy.viewmodel.CategoryCardViewModel;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    private SearchResponse searchResponse;

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardRestaurantInfoBinding binder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.card_restaurant_info, parent, false);
        return new CategoryViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return null == searchResponse ? 0 : searchResponse.getRestaurants().size();
    }

    public void setSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
        notifyDataSetChanged();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private CardRestaurantInfoBinding binder;

        CategoryViewHolder(CardRestaurantInfoBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
            this.binder.setCatCardViewModel(new CategoryCardViewModel());
        }

        void onBind(int position) {

            Restaurant restaurant = searchResponse.getRestaurants().get(position).getRestaurant();
            binder.getCatCardViewModel().setFeatureImage(restaurant.getFeaturedImage());
            binder.getCatCardViewModel().setName(restaurant.getName());
            binder.getCatCardViewModel().setLocation(restaurant.getLocation().getAddress());
            binder.getCatCardViewModel().setCuisine(restaurant.getCuisines());
        }
    }
}
