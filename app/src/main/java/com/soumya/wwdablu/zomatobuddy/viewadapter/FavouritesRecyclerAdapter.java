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
import com.soumya.wwdablu.zomatobuddy.databinding.CardFavouriteInfoBinding;
import com.soumya.wwdablu.zomatobuddy.model.Favourites;
import com.soumya.wwdablu.zomatobuddy.model.favourite.FavouriteData;
import com.soumya.wwdablu.zomatobuddy.viewmodel.FavouritesViewModel;

import java.util.LinkedList;

import timber.log.Timber;

public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.FavouritesViewHolder> {

    private LinkedList<FavouriteData> favouriteDataList;

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardFavouriteInfoBinding binder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.card_favourite_info, parent, false);

        return new FavouritesViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return null == favouriteDataList ? 0 : favouriteDataList.size();
    }

    public void setFavouriteDataList(FavouritesViewModel viewModel) {

        viewModel.getFavouriteList(new Favourites.ITransactionStatus() {
            @Override
            public void onSuccess() {
                //Nothing to do
            }

            @Override
            public void onError() {
                Timber.e("Could not fetch data from favourites.");
            }

            @Override
            public void onFavListRetrieved(LinkedList<FavouriteData> dataList) {
                favouriteDataList = dataList;
                notifyDataSetChanged();
            }
        });
    }

    class FavouritesViewHolder extends RecyclerView.ViewHolder {

        private CardFavouriteInfoBinding binder;

        FavouritesViewHolder(CardFavouriteInfoBinding binder) {

            super(binder.getRoot());
            this.binder = binder;
        }

        void onBind(int position) {

            FavouriteData data = favouriteDataList.get(position);

            binder.tvFavName.setText(data.getResName());
            binder.tvFavLocation.setText(data.getResLocation());
            binder.tvFavCuisine.setText(data.getResCuisines());
        }
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
