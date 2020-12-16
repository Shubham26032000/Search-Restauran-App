package com.example.getnearbyrestrarunt.Models.CollectionModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectionData {


    @SerializedName("collections")
    private List<CollectionList> collectionLists;

    public List<CollectionList> getCollectionLists() {
        return collectionLists;
    }
}
