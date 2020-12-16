package com.example.getnearbyrestrarunt.Models;

public class Location {
    private String address;
    private String city;
    private String latitude;
    private String longitude;
    private String locality;

    public String getLocality() {
        return locality;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
