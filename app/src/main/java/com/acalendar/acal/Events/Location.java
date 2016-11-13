package com.acalendar.acal.Events;

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
    private String streetNumber;

    public Location(double lat, double lng, String address, int postal,
                    String state, String streetName, String streetNumber) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.postal = postal;
        this.state = state;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    public Location(String address) {
        this(-100000, -1000000, address, -100000, "fake state", "fake Stree", "fake street number");
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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}

