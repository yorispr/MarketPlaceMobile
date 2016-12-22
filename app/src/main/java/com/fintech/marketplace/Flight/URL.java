package com.fintech.marketplace.Flight;

/**
 * Created by YORIS on 12/22/16.
 */

public class URL {
    //Contoh URL : https://api-sandbox.tiket.com/flight_api/all_airport?token=2b2f462814af7c0d2e1b40f28e2d9dff5e900f24&output=json
    private String token = "https://api-sandbox.tiket.com/apiv1/payexpress?method=getToken";

    //Contoh URL : http://api-sandbox.tiket.com/search/flight?d=CGK&a=DPS&date=2014-05-25&ret_date=2014-05-30&adult=1&child=0&infant=0&token=ed84e42e9a122f17f108b7310d94c8b87192f2a1&v=3&output=json
    private String searchFlight = "http://api-sandbox.tiket.com/search/flight?";

    //Contoh URL : https://api-sandbox.tiket.com/flight_api/all_airport?token=2b2f462814af7c0d2e1b40f28e2d9dff5e900f24&output=json
    private String allAirport = "https://api-sandbox.tiket.com/flight_api/all_airport?token=";

    public URL(){

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSearchFlight() {
        return searchFlight;
    }

    public void setSearchFlight(String searchFlight) {
        this.searchFlight = searchFlight;
    }

    public String getAllAirport() {
        return allAirport;
    }

    public void setAllAirport(String allAirport) {
        this.allAirport = allAirport;
    }
}
