# ZomatoBuddy  

This is a restaurant search engine by using the **Zomato API**. It displays the information based on the current geolocation of the user and then displays the list of 20 restaurants based on 3 categories, (1) Dine-out, (2) Delivery and (3) Takeaway.  

> Note: I took the existing Zomato application as an inspiration and a baseline to create this application. I am in  no way connected to the actual application or anything related to it. The objective of the application is to use Zomato API and create a friendly application that can display those information, hence the name ZomatoBuddy.  

The Zomato API needs to be defined in the gradle.properties file. You can also modify the default location to use incase the user did not provide the permission for the geolocation.  

This application has been deployed into Google Play Store:  
https://play.google.com/store/apps/details?id=com.soumya.wwdablu.zomatobuddy&hl=en  

**__Information__**  

* Top 20 restaurants  
  This displays the list of 20 restaurants in the location in three categories. We can click on them to dislay their results.  
* Restaurant Images  
  The images are not provided by the Zomato API for the basic version. For this reason , we are using sample images based on the first cuisine mentioned (this is being returned by the Zomato API). Also for the menu and photos, we are using the URL which is returned by the API. Hence to view the actual images, the user needs to visit the URLs provided in the details page.  
  
* Search Feature  
  This perform the search feature based on the query provided by the user. It uses the approach to wait for the user to complete the query and then peform the Search Network API. This can be achieved by using the debounce method in conjunction with the SearchView query change listener. The query change listeners fires all the time, but the actual method of execution will only be called (which will return an observable) once the debounce timeout has been completed, uninterupted. This makes it more flowing with the user and removes the requirement for a search button (which would then require another click action).  
  
* Analytics  
  Using Firebase Analytics to log events like which screen is displayed to the user and the search terms used.  

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
* Firebase Analytics  
* Picasso  
* Dagger 2  
* Parceler  
* Timber  


**__Screenshot__**  

![Screenshot](/screenshot/ZomatoBuddy_Usage_1124_1.gif?raw=true "Sample")  

> The free version of the API does not provide all the required information so the details screen is very bare minimum. But, this should give an idea about the usage of the application.  



**__Changelog__**  

07 - Added Firebase Analytics (current screen and log event - search term)  
06 - Added share feature with UI tweaks. Added powered by Zomato text to show API usage.  
05 - Lint and defect fixes  
04 - Addition of reviews  
03 - Addition of search feature  
02 - Addition of details screen  
01 - Initial checkin  

