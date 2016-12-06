package com.fintech.marketplace;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DetailProductActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    String id_product="";

    private ImageView img_product;
    private TextView txtHarga, txtStok, txtLokasi, txtDeskripsi;

    private ProgressBar progbar;
    private RelativeLayout layout_data;
    ModelItem item = new ModelItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        txtHarga = (TextView)findViewById(R.id.txtHarga);
        txtStok = (TextView)findViewById(R.id.txtStok);
        txtLokasi = (TextView)findViewById(R.id.txtLokasi);
        txtDeskripsi = (TextView)findViewById(R.id.txtDeskripsi);

        progbar = (ProgressBar)findViewById(R.id.progressBar);

        layout_data = (RelativeLayout)findViewById(R.id.layout_data);
        layout_data.setVisibility(View.GONE);

        img_product = (ImageView)findViewById(R.id.img_product);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if(getIntent().getStringExtra("id_product")!=null){
            id_product = getIntent().getStringExtra("id_product");
            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("nama_product"));
            //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
            //img_product.setImageBitmap((Bitmap)getIntent().getParcelableExtra("gambar"));

            Glide.with(getApplicationContext()).load(getIntent().getStringExtra("url_gambar"))
                    .into(img_product)
            ;
        }

        new GetDetailProduct().execute("http://joomla.ternaku.com/?route=feed/rest_api/product&id="+id_product,"");


    }





    private class GetDetailProduct extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute(){
            progbar.setVisibility(View.VISIBLE);
            return;

        }

        @Override
        protected String doInBackground(String... urls) {
            Connection c = new Connection();
            String json = c.GetJSONfromURL(urls[0],urls[1]);
            return json;
        }


        protected void onPostExecute(String result) {
            Log.d("RES", result);
            progbar.setVisibility(View.GONE);
            setDataItem(result);
        }
    }

    private void setDataItem(String result){
        try {
            JSONObject getObjNews = new JSONObject(result);

            if(getObjNews.getBoolean("success")){
                    JSONObject jObj = getObjNews.getJSONObject("product");
                    item.setId_product(jObj.getString("id"));
                    item.setName_Item(jObj.getString("name"));
                    item.setPrice(jObj.getString("price"));
                    item.setDescription(jObj.getString("description"));
                    item.setQuantity(jObj.getInt("quantity"));
                    item.setLocation(jObj.getString("location"));
                    setDatatoTextView(item);
            }
        } catch (JSONException e){e.printStackTrace();}
    }

    private void setDatatoTextView(ModelItem product){
        layout_data.setVisibility(View.VISIBLE);
        txtHarga.setText(": " + product.getPrice());
        txtLokasi.setText(": " +product.getLocation());
        txtStok.setText(": " +String.valueOf(product.getQuantity()));
        txtDeskripsi.setText(product.getDescription());
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
