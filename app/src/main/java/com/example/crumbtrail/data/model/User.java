package com.example.crumbtrail.data.model;

import com.parse.ParseFile;
import com.parse.ParseObject;

public class User extends ParseObject {
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

    public String getPassword() {
        return getString(KEY_PASSWORD);
    }

    public void setPassword(String password) {
        put(KEY_PASSWORD, password);
    }

    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setProfileImage(ParseFile parseFile) {
        put(KEY_PROFILE_IMAGE, parseFile);
    }

    public void setAboutBody(String aboutBody) {
        put(KEY_ABOUT_BODY, aboutBody);
    }

    public String getAboutBody() {
        return getString(KEY_ABOUT_BODY);
    }

    public String stringifyReviewsCount() {
        int reviewCount = getInt(KEY_REVIEWS_COUNT);
        return (reviewCount) + (reviewCount == 1 ? " review" : " reviews");
    }

    public Integer getReviewsCount() {
        return getInt(KEY_REVIEWS_COUNT);
    }

    public String stringifyLikesCount() {
        int likesCount = getInt(KEY_LIKES_COUNT);
        return (likesCount) + (likesCount == 1 ? " review" : " reviews");
    }

    public void setReviewsCount(int newReviewsCount) {
        put(KEY_REVIEWS_COUNT, newReviewsCount);
    }

    public void setLikesCount(int newLikesCount) {
        put(KEY_REVIEWS_COUNT, newLikesCount);
    }

    public void setAverage(int newAverage) {
        if (getNumber(KEY_RATING_AVERAGE) == null) put(KEY_RATING_AVERAGE, newAverage);
        else {
            float oldTotal = ((Float)getNumber(KEY_RATING_AVERAGE)*getReviewsCount());
            float average = (oldTotal + newAverage) / getReviewsCount() + 1;
            put(KEY_RATING_AVERAGE, null);

            // When adding a review, a new average must be calculated
            // When removing a review, a new average must be calculated
            // When updated a review, a new average must be calculated (if average is different)
        }
    }

    public String getEmail() {
        return getString(KEY_EMAIL);
    }

    public void setEmail(String email) {
        put(KEY_EMAIL, email);
    }

    public Boolean getVerified() {
        return getBoolean(KEY_EMAIL_VERIFIED);
    }

    public void setEmailVerified(Boolean verified) {
        put(KEY_EMAIL_VERIFIED, verified);
    }

}
