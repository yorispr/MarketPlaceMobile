package com.fintech.marketplace.Flight;

import java.util.ArrayList;

/**
 * Created by YORIS on 12/21/16.
 */

public class FlightResultModel {
    private String flight_id;
    private String airlines_name;
    private String departure_city;
    private String arrival_city;
    private String stop;
    private double price_value;
    private double price_adult;
    private double price_child;
    private double price_infant;
    private int has_food;
    private double check_in_baggage;
    private int is_promo;
    private boolean airport_tax;
    private String check_in_baggage_unit;
    private String simple_departure_time;
    private String simple_arrival_time;
    private String long_via;
    private String departure_city_name;
    private String arrival_city_name;
    private String full_via;
    private String markup_price_string;
    private int need_baggage;
    private boolean best_deal;
    private String duration;
    private String image;
    private String departure_flight_date;
    private String departure_flight_date_str;
    private String departure_flight_date_str_short;
    private String arrival_flight_date;
    private String arrival_flight_date_str;
    private String arrival_flight_date_str_short;

    private ArrayList<FlightInfoModel> flight_info;


    public FlightResultModel(){

    }

    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

    public String getAirlines_name() {
        return airlines_name;
    }

    public void setAirlines_name(String airlines_name) {
        this.airlines_name = airlines_name;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
    }

    public String getArrival_city() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public double getPrice_value() {
        return price_value;
    }

    public void setPrice_value(double price_value) {
        this.price_value = price_value;
    }

    public double getPrice_adult() {
        return price_adult;
    }

    public void setPrice_adult(double price_adult) {
        this.price_adult = price_adult;
    }

    public double getPrice_child() {
        return price_child;
    }

    public void setPrice_child(double price_child) {
        this.price_child = price_child;
    }

    public double getPrice_infant() {
        return price_infant;
    }

    public void setPrice_infant(double price_infant) {
        this.price_infant = price_infant;
    }

    public int getHas_food() {
        return has_food;
    }

    public void setHas_food(int has_food) {
        this.has_food = has_food;
    }

    public double getCheck_in_baggage() {
        return check_in_baggage;
    }

    public void setCheck_in_baggage(double check_in_baggage) {
        this.check_in_baggage = check_in_baggage;
    }

    public int getIs_promo() {
        return is_promo;
    }

    public void setIs_promo(int is_promo) {
        this.is_promo = is_promo;
    }

    public boolean isAirport_tax() {
        return airport_tax;
    }

    public void setAirport_tax(boolean airport_tax) {
        this.airport_tax = airport_tax;
    }

    public String getCheck_in_baggage_unit() {
        return check_in_baggage_unit;
    }

    public void setCheck_in_baggage_unit(String check_in_baggage_unit) {
        this.check_in_baggage_unit = check_in_baggage_unit;
    }

    public String getSimple_departure_time() {
        return simple_departure_time;
    }

    public void setSimple_departure_time(String simple_departure_time) {
        this.simple_departure_time = simple_departure_time;
    }

    public String getSimple_arrival_time() {
        return simple_arrival_time;
    }

    public void setSimple_arrival_time(String simple_arrival_time) {
        this.simple_arrival_time = simple_arrival_time;
    }

    public String getLong_via() {
        return long_via;
    }

    public void setLong_via(String long_via) {
        this.long_via = long_via;
    }

    public String getDeparture_city_name() {
        return departure_city_name;
    }

    public void setDeparture_city_name(String departure_city_name) {
        this.departure_city_name = departure_city_name;
    }

    public String getArrival_city_name() {
        return arrival_city_name;
    }

    public void setArrival_city_name(String arrival_city_name) {
        this.arrival_city_name = arrival_city_name;
    }

    public String getFull_via() {
        return full_via;
    }

    public void setFull_via(String full_via) {
        this.full_via = full_via;
    }

    public String getMarkup_price_string() {
        return markup_price_string;
    }

    public void setMarkup_price_string(String markup_price_string) {
        this.markup_price_string = markup_price_string;
    }

    public int getNeed_baggage() {
        return need_baggage;
    }

    public void setNeed_baggage(int need_baggage) {
        this.need_baggage = need_baggage;
    }

    public boolean isBest_deal() {
        return best_deal;
    }

    public void setBest_deal(boolean best_deal) {
        this.best_deal = best_deal;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeparture_flight_date() {
        return departure_flight_date;
    }

    public void setDeparture_flight_date(String departure_flight_date) {
        this.departure_flight_date = departure_flight_date;
    }

    public String getDeparture_flight_date_str() {
        return departure_flight_date_str;
    }

    public void setDeparture_flight_date_str(String departure_flight_date_str) {
        this.departure_flight_date_str = departure_flight_date_str;
    }

    public String getDeparture_flight_date_str_short() {
        return departure_flight_date_str_short;
    }

    public void setDeparture_flight_date_str_short(String departure_flight_date_str_short) {
        this.departure_flight_date_str_short = departure_flight_date_str_short;
    }

    public String getArrival_flight_date() {
        return arrival_flight_date;
    }

    public void setArrival_flight_date(String arrival_flight_date) {
        this.arrival_flight_date = arrival_flight_date;
    }

    public String getArrival_flight_date_str() {
        return arrival_flight_date_str;
    }

    public void setArrival_flight_date_str(String arrival_flight_date_str) {
        this.arrival_flight_date_str = arrival_flight_date_str;
    }

    public String getArrival_flight_date_str_short() {
        return arrival_flight_date_str_short;
    }

    public void setArrival_flight_date_str_short(String arrival_flight_date_str_short) {
        this.arrival_flight_date_str_short = arrival_flight_date_str_short;
    }

    public ArrayList<FlightInfoModel> getFlight_info() {
        return flight_info;
    }

    public void setFlight_info(ArrayList<FlightInfoModel> flight_info) {
        this.flight_info = flight_info;
    }
}
