package com.example.getnearbyrestrarunt.Models;

import com.google.gson.annotations.SerializedName;

public class RestraruntsList {

    @SerializedName("restaurant")
    private Restaurants restaurants;

    public Restaurants getRestaurants() {
        return restaurants;
    }
}
