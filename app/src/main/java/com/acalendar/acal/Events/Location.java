package com.acalendar.acal.Events;

import java.util.Map;

/**
 * Frontend model for Location
 */
public class Location {
    private double lat;
    private double lng;
    private String address;
    private int postal;
    private String state;
    private String streetName;
    private int streetNumber;

    public Location(double lat, double lng, String address, int postal,
                    String state, String streetName, int streetNumber) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.postal = postal;
        this.state = state;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    public Location(String address) {
        this(-100000, -1000000, address, -100000, "fake state", "fake Stree", -1010101);
    }

    public double getLatitude() {
        return lat;
    }

    public void setLatitude(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return lng;
    }

    public void setLongitude(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostal() {
        return postal;
    }

    public void setPostal(int postal) {
        this.postal = postal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    /*
    "location" = {
        "lat" = "$input.params('lat')",
        "lng" = "$input.params('lng')",
        "address" = "$input.params('address')",
        "postal" = "$input.params('postal')",
        "state" = "$input.params('state')",
        "streetName" = "$input.params('streetName')",
        "streetNumber" = "$input.params('streetNumber')"}
    */
    public static Location parseLocation(Map<String, Object> location) {
        double lat = (Double) location.get("lat");
        double lng = (Double) location.get("lng");
        String address = (String) location.get("address");
        int postal = (Integer) location.get("postal");
        String state = (String) location.get("state");
        int streetName = (Integer) location.get("streetName");
        String streetNumber = (String) location.get("streetNumber");
        Location l = new Location(lat, lng, address,
                postal, state, streetNumber, streetName);
        return l;
    }

}

