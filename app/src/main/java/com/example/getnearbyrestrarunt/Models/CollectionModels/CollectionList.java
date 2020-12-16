package com.example.getnearbyrestrarunt.Models.CollectionModels;

import com.google.gson.annotations.SerializedName;

public class CollectionList {

    @SerializedName("collection")
    private CollectionMainData collections;

    public CollectionMainData getCollections() {
        return collections;
    }
}
