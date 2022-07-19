package com.example.crumbtrail;

import android.app.Application;

import com.example.crumbtrail.data.model.Comment;
import com.example.crumbtrail.data.model.MapMarker;
import com.example.crumbtrail.data.model.Review;
import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class CrumbTrailApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See https://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Review.class);
        ParseObject.registerSubclass(MapMarker.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("LFrQVXmbxrtLMUHLwxEyW7ZprEQvbl8v34JXR9xc") // should correspond to Application Id env variable
                .clientKey("2S8qeKUrk9xjc2qRF6xhZnDHz908omjppAs7Vux4")  // should correspond to Client key env variable
                .server("https://parseapi.back4app.com").build());
    }
}
