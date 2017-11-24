package com.soumya.wwdablu.zomatobuddy.viewadapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.databinding.CardRestaurantInfoBinding;
import com.soumya.wwdablu.zomatobuddy.model.search.Restaurant;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;
import com.soumya.wwdablu.zomatobuddy.viewmodel.CategoryCardViewModel;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    public interface IRestaurantAction {
        void onClick(Restaurant restaurant);
    }

    private SearchResponse searchResponse;
    private IRestaurantAction restaurantActionImpl;

    public CategoryRecyclerAdapter(IRestaurantAction implementation) {
        this.restaurantActionImpl = implementation;
    }

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

    /*
     * View Holder Class
     */
    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private CardRestaurantInfoBinding binder;

        CategoryViewHolder(CardRestaurantInfoBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
            this.binder.setCatCardViewModel(new CategoryCardViewModel());

            this.binder.getRoot().setOnClickListener(clickListener);
        }

        void onBind(int position) {

            Restaurant restaurant = searchResponse.getRestaurants().get(position).getRestaurant();
            binder.getCatCardViewModel().setName(restaurant.getName());
            binder.getCatCardViewModel().setLocation(restaurant.getLocation().getAddress());
            binder.getCatCardViewModel().setCuisine(restaurant.getCuisines());
            binder.getCatCardViewModel().setFeatureImage(restaurant.getFeaturedImage());
        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Null guard check
                if(null == restaurantActionImpl) {
                    return;
                }

                restaurantActionImpl.onClick(searchResponse.getRestaurants().
                        get(getAdapterPosition()).getRestaurant());
            }
        };
    }

    public static class CardDecorator extends RecyclerView.ItemDecoration {

        private int margins;

        public CardDecorator(Context context, @DimenRes int offset) {
            margins = context.getResources().getDimensionPixelSize(offset);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(margins, margins, margins, margins);
        }
    }
}
