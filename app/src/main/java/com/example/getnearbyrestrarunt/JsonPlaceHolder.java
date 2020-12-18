package com.example.getnearbyrestrarunt;

import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionData;
import com.example.getnearbyrestrarunt.Models.LocationSearchData.LocationData;
import com.example.getnearbyrestrarunt.Models.SearchRestrarunts;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface JsonPlaceHolder {

    @Headers({"Accept: application/json",
            "user-key: 94e6664e979eb4d1b9b7406d4a864daf"})
    @GET("api/v2.1/search")
    Call<SearchRestrarunts> getRestaurants(
            @Query("q") String randomSearch,
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("entity_id") double entitype
    );

    @Headers({"Accept: application/json",
            "user-key: 94e6664e979eb4d1b9b7406d4a864daf"})
    @GET("api/v2.1/search")
    Call<SearchRestrarunts> getRestaurants(

            @Query("collection_id") int collectionId
    );

    @Headers({"Accept: application/json",
            "user-key: 94e6664e979eb4d1b9b7406d4a864daf"})
    @GET("api/v2.1/collections")
    Call<CollectionData> getCollection(
            @Query("city_id") int cityId
    );

    @Headers({"Accept: application/json",
            "user-key: 94e6664e979eb4d1b9b7406d4a864daf"})
    @GET("api/v2.1/locations")
    Call<LocationData> getLocationCords(
            @Query("query") String location
    );




}
