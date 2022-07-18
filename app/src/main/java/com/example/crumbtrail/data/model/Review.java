package com.example.crumbtrail.data.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("Review")
public class Review extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_BODY = "body";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_RATING = "rating";
    public static final String KEY_FCDID = "fcdId";

    public String getBody() {
        return getString(KEY_BODY);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }


    public void setBody(String body) {
        put(KEY_BODY, body);
    }

    public Long getFCDId() {
        return getLong(KEY_FCDID);
    }

    public void setFCDId(Long fcdId) {
        put(KEY_FCDID, String.valueOf(fcdId));
    }

    public Number getRating() {
        return getNumber(KEY_RATING);
    }

    public void setRating(Float rating) {
        put(KEY_RATING, rating);
    }

//    public ParseUser getParseUser() {
//        return (ParseUser) getParseObject(KEY_AUTHOR);
//    }

//    public void setParseUser(ParseUser ParseUser) {
//        put(KEY_AUTHOR, ParseUser);
//    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public void setComments(List<Comment> newCommentsList) {
        put(KEY_COMMENTS, newCommentsList);
    }

    public List<Comment> getComments() {
        List<Comment> comments = getList(KEY_COMMENTS);
        if (comments == null) {
            return new ArrayList<>();
        }
        return comments;
    }

    public void setLikedBy(List<ParseUser> newLikeBy) {
        put(KEY_LIKES, newLikeBy);
    }

    public List<ParseUser> getLikedBy() {
        List<ParseUser> likedBy = getList(KEY_LIKES);
        if (likedBy == null) {
            return new ArrayList<>();
        }
        return likedBy;
    }

    public boolean isLikedBy(ParseUser user) {
        List<ParseUser> likedBy = getLikedBy();
        if (likedBy.contains(user)) {
            return true;
        }
        return false;
    }

    public String getLikesCount() {
        int likeCount = getLikedBy().size();
        return (likeCount) + (likeCount == 1 ? " like" : " likes");
    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }
}