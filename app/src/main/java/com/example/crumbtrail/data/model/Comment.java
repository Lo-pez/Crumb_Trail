package com.example.crumbtrail.data.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_PARENT_REVIEW = "parentReview";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_BODY = "body";

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

    public String getLikesCount() {
        int likeCount = getLikedBy().size();
        return (likeCount) + (likeCount == 1 ? " like" : " likes");
    }

    public String getBody() {
        return getString(KEY_BODY);
    }

    public void setBody(String body) {
        put(KEY_BODY, body);
    }

    public ParseUser getParseUser() {
        return (ParseUser) getParseObject(KEY_AUTHOR);
    }

    public void setParseUser(ParseUser ParseUser) {
        put(KEY_AUTHOR, ParseUser);
    }

    public Review getReview(){
        return (Review) getParseObject(KEY_PARENT_REVIEW);
    }

    public void setReview(Review review) {
        put(KEY_PARENT_REVIEW, review);
    }
}
