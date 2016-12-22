package com.fintech.marketplace.Flight;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fintech.marketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SearchResultActivity extends AppCompatActivity {

    private ArrayList<FlightResultModel>flighresultList = new ArrayList<>();

    private ListView listResult;

    FlightResultAdapter flight_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listResult = (ListView)findViewById(R.id.listPenerbangan);

        flight_adapter = new FlightResultAdapter(this,R.layout.holder_flight_result,flighresultList);
        listResult.setAdapter(flight_adapter);

        convertToJson(getIntent().getStringExtra("json"));
    }

    private void convertToJson(String json){
        try {
            JSONObject resobj = new JSONObject(json);
            JSONObject search_queries = resobj.getJSONObject("go_det");

            JSONObject depobj = search_queries.getJSONObject("dep_airport");
            String from = depobj.getString("short_name");

            JSONObject arrobj = search_queries.getJSONObject("arr_airport");
            String to = arrobj.getString("short_name");
            String tgl = search_queries.getString("date");

            getSupportActionBar().setTitle(from + " \u2192 " +to);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(tgl));// all done

            getSupportActionBar().setSubtitle(ToIdDate(cal));

            JSONObject flight = resobj.getJSONObject("departures");
            JSONArray flightarray = flight.getJSONArray("result");

            Log.d("Jumlah",String.valueOf(flightarray.length()));
            for(int i = 0 ; i<flightarray.length() ; i++){
                JSONObject fl = flightarray.getJSONObject(i);
                FlightResultModel fr = new FlightResultModel();
                fr.setAirlines_name(fl.getString("airlines_name"));
                fr.setPrice_value(fl.getDouble("price_value"));
                fr.setSimple_arrival_time(fl.getString("simple_arrival_time"));
                fr.setSimple_departure_time(fl.getString("simple_departure_time"));
                fr.setDuration(fl.getString("duration"));
                fr.setStop(fl.getString("stop"));
                fr.setImage(fl.getString("image"));
                flighresultList.add(fr);
            }
            flight_adapter.notifyDataSetChanged();

        }catch(JSONException je){
            je.printStackTrace();
        }
        catch(ParseException de){de.printStackTrace();}



    }

    private String ToIdDate(Calendar cal){
        String IDDateArray[] = {"Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
        String IDMonthArray[] = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};

        String date = IDDateArray[cal.get(Calendar.DAY_OF_WEEK) - 1]+", "+cal.get(Calendar.DAY_OF_MONTH)+" "+IDMonthArray[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.YEAR);

        Log.d("TGL",String.valueOf(cal.get(Calendar.DAY_OF_WEEK)));

        return date;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
