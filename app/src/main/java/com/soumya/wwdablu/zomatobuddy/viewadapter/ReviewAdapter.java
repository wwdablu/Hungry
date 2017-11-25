package com.soumya.wwdablu.zomatobuddy.viewadapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.soumya.wwdablu.zomatobuddy.BuildConfig;
import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.databinding.CardReviewBinding;
import com.soumya.wwdablu.zomatobuddy.model.ReviewModel;
import com.soumya.wwdablu.zomatobuddy.model.reviews.ReviewResponse;
import com.soumya.wwdablu.zomatobuddy.model.reviews.Review;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ReviewModel reviewModel;
    private DisposableObserver<ReviewResponse> disposableObserver;
    private ReviewResponse reviewReviewResponse;

    public ReviewAdapter(final String resId) {

        reviewModel = new ReviewModel(BuildConfig.ZOMATO_BASE_URL);

        disposableObserver = reviewModel.getReviews(resId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<ReviewResponse>() {
                @Override
                public void onNext(ReviewResponse reviewResponse) {
                    reviewReviewResponse = reviewResponse;
                    notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ReviewViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.card_review,
                parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return null == reviewReviewResponse ? 0 : reviewReviewResponse.getUserReviews().size();
    }

    public void clean() {

        if(null != disposableObserver && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private CardReviewBinding binder;

        ReviewViewHolder(CardReviewBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }

        void onBind(int position) {

            Review review = reviewReviewResponse.getUserReviews().get(position).getReview();

            binder.rbRating.setRating(((float) review.getRating()));
            binder.tvRatingText.setText(review.getRatingText());
            binder.tvReviewerName.setText(review.getUser().getName());
            binder.tvReviewDate.setText(review.getReviewTimeFriendly());
            binder.tvReviewContent.setText(review.getReviewText());
        }
    }
}
