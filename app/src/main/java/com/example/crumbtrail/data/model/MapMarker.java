package com.example.crumbtrail.data.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Model for the MapMarker objects. The data used here is obtained from Parse.
 */
@ParseClassName("MapMarker")
public class MapMarker extends ParseObject {
    public static final String KEY_NAME = "name";
    public static final String KEY_DISCOUNT = "discount";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_FOOD = "food";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public String getDiscount() {
        return getString(KEY_DISCOUNT);
    }

    public void setDiscount(String discount) {
        put(KEY_DISCOUNT, discount);
    }

    public String getFood() {
        return getString(KEY_FOOD);
    }

    public void setFood(String food) {
        put(KEY_FOOD, food);
    }

    public void setLocation(ParseGeoPoint latLng) { put(KEY_LOCATION, latLng); }

    public ParseGeoPoint getLocation() { return getParseGeoPoint(KEY_LOCATION); }
}