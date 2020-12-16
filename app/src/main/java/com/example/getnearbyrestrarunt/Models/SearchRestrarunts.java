package com.example.getnearbyrestrarunt.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRestrarunts {

    @SerializedName("restaurants")
    private List<RestraruntsList> restraruntsLists;

    public List<RestraruntsList> getRestraruntsLists() {
        return restraruntsLists;
    }
}
