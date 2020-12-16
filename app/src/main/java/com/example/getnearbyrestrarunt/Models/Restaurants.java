package com.example.getnearbyrestrarunt.Models;

import com.example.getnearbyrestrarunt.Models.LocationSearchData.Rating;
import com.google.gson.annotations.SerializedName;

public class Restaurants {

    @SerializedName("user_rating")
    private Rating rating;
    private String name;
    private String url;
    private String cuisines;
    @SerializedName("featured_image")
    private String featured_image;
    private String phone_numbers;
    private Location location;

    private String timings;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCuisines() {
        return cuisines;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public String getPhone_numbers() {
        return phone_numbers;
    }

    public Location getLocation() {
        return location;
    }

    public String getTimings() {
        return timings;
    }

    public Rating getRating() {
        return rating;
    }
}
