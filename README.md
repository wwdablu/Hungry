# ZomatoBuddy  

This is a restaurant search engine by using the **Zomato API**. It displays the information based on the current geolocation of the user and then displays the list of 20 restaurants based on 3 categories, (1) Dine-out, (2) Delivery and (3) Takeaway.  

> Note: I took the existing Zomato application as an inspiration and a baseline to create this application. I am in  no way connected to the actual application or anything related to it. The objective of the application is to use Zomato API and create a friendly application that can display those information, hence the name ZomatoBuddy.  

The Zomato API needs to be defined in the gradle.properties file. You can also modify the default location to use incase the user did not provide the permission for the geolocation.  

**__Libraries used during development:__**  

* Recycler View  
* Card View  
* Design Library  
    * NestedScrollView
    * CollapsingToolbarLayout  
    * FragmentStatePageAdapter  
    * ViewPager  
    * CoordinatorLayout  
* Fused Location Provider (Google Play services - Location)  
* Retrofit  
* RxJava  
* Picasso  
* Dagger 2  
* Parceller  
* Timber  
* Hawk  
