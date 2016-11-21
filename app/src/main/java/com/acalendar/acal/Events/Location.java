package com.acalendar.acal.Events;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Frontend model for Location, maynot be needed at all
 */
public class Location implements Serializable {
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
        this(-1000.01, -10000.01, address, -100000, "fake state", "fake Stree", -1010101);
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


    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("lat", new Double(this.lat));
        info.put("lng", new Double(this.lng));
        info.put("postal", new Integer(this.postal));
        info.put("address", this.address);
        info.put("state", this.state);
        info.put("streetName", this.streetName);
        info.put("streetNumber", this.streetNumber);
        return info;
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
        if (location == null || location.isEmpty()) {
            return null;
        }
        double lat = (Double) location.get("lat");
        double lng = (Double) location.get("lng");
        String address = (String) location.get("address");
        int postal = ((Double)location.get("postal")).intValue();
        String state = (String) location.get("state");
        String streetName = (String)location.get("streetName");
        int streetNumber = ((Double)location.get("streetNumber")).intValue();
        Location l = new Location(lat, lng, address,
                postal, state, streetName, streetNumber);
        return l;
    }

}

