package com.fintech.marketplace.Flight;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fintech.marketplace.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SearchFlightActivity extends AppCompatActivity {

    private SearchableSpinner spinAsal, spinTujuan;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private boolean isTujuan=false, isAsal=false, isTglKembali=false, isRoundTrip = false;

    private TextView txtTgl,txtTglKembali;
    SessionManager sessionManager ;

    private ArrayList<AirportModel> AirportList = new ArrayList<AirportModel>();

    private ArrayList<String> AirportList_test = new ArrayList<String>();

    ArrayAdapter<String> SpinnerAdapter;

    private String tgl_keberangkatan="", tgl_kembali="";
    private RelativeLayout layouttgl,layouttglkembali;

    private RadioGroup radioGroup;

    private Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });fab.hide();

        sessionManager = new SessionManager(SearchFlightActivity.this);

        spinAsal = (SearchableSpinner)findViewById(R.id.spinAsalBandara);
        spinTujuan = (SearchableSpinner)findViewById(R.id.spinBandaraTujuan);

        SpinnerAdapter= new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,AirportList_test);
        spinAsal.setAdapter(SpinnerAdapter);
        spinTujuan.setAdapter(SpinnerAdapter);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newDate = Calendar.getInstance();
        tgl_keberangkatan = dateFormatter.format(newDate.getTime());

        layouttgl = (RelativeLayout)findViewById(R.id.layoutTgl);
        layouttglkembali = (RelativeLayout)findViewById(R.id.layoutTglKembali);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioSkaliJalan){
                    isRoundTrip = false;
                    layouttglkembali.setVisibility(View.GONE);
                }else if(checkedId == R.id.radioPulangPergi){
                    isRoundTrip = true;
                    layouttglkembali.setVisibility(View.VISIBLE);
                    txtTglKembali.setText(txtTgl.getText().toString());
                    tgl_kembali = tgl_keberangkatan;
                }
                else{
                    isRoundTrip = false;
                }
            }
        });

        txtTgl = (TextView)findViewById(R.id.txtTgl);
        txtTglKembali = (TextView)findViewById(R.id.txtTglKembali);

        txtTgl.setText(ToIdDate(newDate));


        setDateTimeField();


        layouttgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
                isTglKembali=false;
            }
        });
        layouttglkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
                isTglKembali = true;
            }
        });

        if(!sessionManager.isLoggedIn()) {
            JSONRequest("https://api-sandbox.tiket.com/apiv1/payexpress?method=getToken&secretkey=56c8624d6a62e1ab22f0d9915ff2d43c&output=json", true, false, false);
        }else{
            String url = "https://api-sandbox.tiket.com/flight_api/all_airport?token="+ sessionManager.GetToken() +"&output=json";
            JSONRequest(url,false,false,true);
        }


        btnSearch = (Button)findViewById(R.id.btnCari);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String asal = spinAsal.getSelectedItem().toString().substring(spinAsal.getSelectedItem().toString().indexOf("(")+1,spinAsal.getSelectedItem().toString().indexOf(")"));
                String tujuan = spinTujuan.getSelectedItem().toString().substring(spinTujuan.getSelectedItem().toString().indexOf("(")+1,spinTujuan.getSelectedItem().toString().indexOf(")"));
                String url = "";
                if(isRoundTrip){
                    url = "http://api-sandbox.tiket.com/search/flight?d="+asal+"&a="+tujuan+"&date="+tgl_keberangkatan+"&ret_date="+tgl_kembali+"&adult=1&child=0&infant=0&token="+sessionManager.GetToken()+"&output=json";
                }else{
                    //url = "http://api-sandbox.tiket.com/search/flight?d="+asal+"&a="+tujuan+"&date="+tgl_keberangkatan+"&adult=1&child=0&infant=0&token="+sessionManager.GetToken()+"&v=3&output=json";
                    url = "http://api-sandbox.tiket.com/search/flight?d="+"CGK"+"&a="+"DPS"+"&date="+tgl_keberangkatan+"&adult=1&child=0&infant=0&token="+sessionManager.GetToken()+"&v=3&&output=json";
                }

                JSONRequest(url,false,true,false);
            }
        });
    }


    public void JSONRequest(String url, final boolean isGetToken, final boolean isSearchFlight, final boolean isGetAirport){
        Log.d("URL",url);
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (isGetToken) {
                                Log.d("token",response.getString("token"));
                                sessionManager.createLoginSession("Yoris", "yoris@gmail.com", response.getString("token"));
                                String url = "https://api-sandbox.tiket.com/flight_api/all_airport?token="+ sessionManager.GetToken() +"&output=json";
                                JSONRequest(url,false,false,true);
                            }
                            if(isSearchFlight){
                                Log.d("flight",response.toString());
                                Intent i = new Intent(SearchFlightActivity.this,SearchResultActivity.class);
                                i.putExtra("json",response.toString());

                                startActivity(i);
                            }
                            if(isGetAirport){
                                JSONObject apobject = response.getJSONObject("all_airport");
                                JSONArray aparray = apobject.getJSONArray("airport");
                                for(int i=0;i<aparray.length();i++){
                                    JSONObject apobj = aparray.getJSONObject(i);
                                    AirportModel apmodel = new AirportModel();
                                    apmodel.setAirport_name(apobj.getString("airport_name"));
                                    apmodel.setAirport_code(apobj.getString("airport_code"));
                                    apmodel.setLocation_name(apobj.getString("location_name"));
                                    apmodel.setCountry_id(apobj.getString("country_id"));
                                    apmodel.setCountry_name(apobj.getString("country_name"));
                                    AirportList.add(apmodel);
                                    AirportList_test.add(apmodel.getLocation_name()+" ("+apmodel.getAirport_code()+")");
                                }
                                SpinnerAdapter.notifyDataSetChanged();
                                Log.d("JSONRequest", response.toString());

                            }
                        }catch(JSONException je){je.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("SearchFlightActivity","Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,"SearchFLightActivity");
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if(!isTglKembali) {
                    tgl_keberangkatan = dateFormatter.format(newDate.getTime());
                    txtTgl.setText(ToIdDate(newDate));
                }else{
                    tgl_kembali = dateFormatter.format(newDate.getTime());
                    txtTglKembali.setText(ToIdDate(newDate));
                }
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private String ToIdDate(Calendar cal){
        String IDDateArray[] = {"Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
        String IDMonthArray[] = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};

        String date = IDDateArray[cal.get(Calendar.DAY_OF_WEEK) - 1]+", "+cal.get(Calendar.DAY_OF_MONTH)+" "+IDMonthArray[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.YEAR);

        Log.d("TGL",String.valueOf(cal.get(Calendar.DAY_OF_WEEK)));

        return date;
    }
}
