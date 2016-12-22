package com.fintech.marketplace.Flight;

/**
 * Created by YORIS on 12/21/16.
 */

public class AirportModel {
    private String airport_name;
    private String airport_code;
    private String location_name;
    private String country_id;
    private String country_name;


    public AirportModel(){

    }
    public AirportModel (String airport_name, String airport_code, String location_name, String country_id, String country_name){
        this.setAirport_name(airport_name);
        this.setAirport_code(airport_code);
        this.setLocation_name(location_name);
        this.setCountry_id(country_id);
        this.setCountry_name(country_name);
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getAirport_code() {
        return airport_code;
    }

    public void setAirport_code(String airport_code) {
        this.airport_code = airport_code;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
