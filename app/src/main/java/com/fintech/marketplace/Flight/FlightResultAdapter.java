package com.fintech.marketplace.Flight;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fintech.marketplace.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YORIS on 12/22/16.
 */

public class FlightResultAdapter extends ArrayAdapter<FlightResultModel> {
    private Activity activity;
    private List<FlightResultModel> flight_list;
    private static LayoutInflater inflater=null;
    private int layout;
    private List<FlightResultModel> List_flight_result = new ArrayList<FlightResultModel>();
    Activity act;

    public FlightResultAdapter(Activity a, int layout, List<FlightResultModel>items) {
        super(a, layout, items);
        this.activity = a;
        this.layout = layout;
        List_flight_result = items;
        act = a;
    }

    @Override
    public int getCount() {
        return List_flight_result.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        holder = new ViewHolder();
        view.setTag(holder);

        holder.txtMaskapai = (TextView)view.findViewById(R.id.txtMaskapai);
        holder.txtJamBerangkat = (TextView)view.findViewById(R.id.txtJamBerangkat);
        holder.txtJamTiba = (TextView)view.findViewById(R.id.txtJamTiba);
        holder.txtDurasi = (TextView)view.findViewById(R.id.txtDurasi);
        holder.txtHarga = (TextView)view.findViewById(R.id.txtHarga);
        holder.txtLangsung = (TextView)view.findViewById(R.id.txtLangsung);
        holder.imgLogoMaskapai = (ImageView)view.findViewById(R.id.imgLogoMaskapai);

        FlightResultModel fr;
        fr = List_flight_result.get(position);
        // Setting all values in listview
        holder.txtMaskapai.setText(fr.getAirlines_name());
        holder.txtJamBerangkat.setText(fr.getSimple_departure_time()+" ");
        holder.txtJamTiba.setText(" "+fr.getSimple_arrival_time());
        holder.txtDurasi.setText("Durasi : "+fr.getDuration());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(fr.getPrice_value());

        holder.txtHarga.setText(yourFormattedString);
        holder.txtLangsung.setText(fr.getStop());

        Glide.with(activity).load(fr.getImage())
                .into(holder.imgLogoMaskapai);

        Log.d("Maskapai : ",fr.getAirlines_name());
        return view;
    }

    public class ViewHolder {
        public TextView txtMaskapai, txtJamBerangkat, txtJamTiba, txtDurasi, txtHarga, txtLangsung;
        public ImageView imgLogoMaskapai;
    }
}
