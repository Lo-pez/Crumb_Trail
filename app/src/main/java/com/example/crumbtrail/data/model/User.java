package com.example.crumbtrail.data.model;

import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * Model for the User objects. The data used here is obtained from Parse.
 */
public class User extends ParseUser {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_EMAIL_VERIFIED = "emailVerified";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PROFILE_IMAGE = "profileImage";
    public static final String KEY_ABOUT_BODY = "aboutBody";
    public static final String KEY_REVIEWS_COUNT = "reviewsCount";
    public static final String KEY_LIKES_COUNT = "likesCount";
    public static final String KEY_RATING_AVERAGE = "ratingAverage";

    public String getUsername() {
        return getString(KEY_USERNAME);
    }

    public void setUsername(String username) {
        put(KEY_USERNAME, username);
    }

    public void setPassword(String password) {
        put(KEY_PASSWORD, password);
    }

    public String getEmail() {
        return getString(KEY_EMAIL);
    }

    public void setEmail(String email) {
        put(KEY_EMAIL, email);
    }


}
