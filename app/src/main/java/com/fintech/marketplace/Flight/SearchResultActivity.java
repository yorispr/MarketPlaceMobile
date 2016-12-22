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
    private ArrayList<String>flighresultList_test = new ArrayList<>();

    private ListView listResult;
    private ArrayAdapter<String> adapterpenerbangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });fab.hide();
        listResult = (ListView)findViewById(R.id.listPenerbangan);
        adapterpenerbangan= new ArrayAdapter<String>(SearchResultActivity.this, android.R.layout.simple_list_item_1,flighresultList_test);
        listResult.setAdapter(adapterpenerbangan);

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
            getSupportActionBar().setTitle(from + " -> " +to);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(tgl));// all done

            getSupportActionBar().setSubtitle(ToIdDate(cal));

            JSONObject flight = resobj.getJSONObject("departures");
            JSONArray flightarray = flight.getJSONArray("result");

            for(int i = 0 ; i<flightarray.length() ; i++){
                JSONObject fl = flightarray.getJSONObject(i);
                FlightResultModel fr = new FlightResultModel();
                fr.setAirlines_name(fl.getString("airlines_name"));
                fr.setPrice_value(fl.getDouble("price_value"));
                fr.setSimple_arrival_time(fl.getString("simple_arrival_time"));
                fr.setSimple_departure_time(fl.getString("simple_departure_time"));
                flighresultList_test.add(fr.getAirlines_name() + ", "+fr.getPrice_value() +"\nBerangkat : "+fr.getSimple_departure_time()+"\nTiba : "+fr.getSimple_arrival_time());

            }
            adapterpenerbangan.notifyDataSetChanged();

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
