package com.soumya.wwdablu.zomatobuddy.dagger;

import com.soumya.wwdablu.zomatobuddy.activity.DashboardActivity;
import com.soumya.wwdablu.zomatobuddy.activity.SplashActivity;
import com.soumya.wwdablu.zomatobuddy.fragment.RestaurantDetails;
import com.soumya.wwdablu.zomatobuddy.model.RestaurantDetailsModel;
import com.soumya.wwdablu.zomatobuddy.model.SearchModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = NetworkModule.class)
@Singleton
public interface NetworkComponent {

    void inject(SplashActivity splashActivity);
    void inject(DashboardActivity dashboardActivity);

    void inject(SearchModel searchModel);
    void inject(RestaurantDetailsModel restaurantDetailsModel);
}
