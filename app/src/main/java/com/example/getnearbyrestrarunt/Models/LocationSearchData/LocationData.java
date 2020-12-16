package com.example.getnearbyrestrarunt.Models.LocationSearchData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//https://developers.zomato.com/api/v2.1/locations?query=gurugram

public class LocationData {

    @SerializedName("location_suggestions")
    private List<LocationDataList> location_suggestions;

    public List<LocationDataList> getLocation_suggestions() {
        return location_suggestions;
    }
}
