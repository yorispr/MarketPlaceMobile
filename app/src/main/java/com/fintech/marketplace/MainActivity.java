package com.fintech.marketplace;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
    @Bind(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @Bind(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @Bind(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    NavigationTabBar navigationTabBar;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<ModelItem> itemList;
    UrlList url = new UrlList();

    private ProgressBar progbar;

    private boolean isHideToolbarView = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(false);
            actionbar.setDisplayShowTitleEnabled(false);
            actionbar.setTitle("Home");
        }

        //Set Toolbar Floating Button-----------------------------
        navigationTabBar = (NavigationTabBar) findViewById(R.id.navigation_main_activity);
        InitUINavTabBar();
        initUiCollapse();

        //Set Data List--------------------------------------------
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList);
        progbar = (ProgressBar)findViewById(R.id.progressBar3);



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        new GetDataItem().execute("http://joomla.ternaku.com/?route=feed/rest_api/GetProductsForMobile","");

    }


    private class GetDataItem extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute(){
            recyclerView.setVisibility(View.GONE);
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
            setDataItem(result);
            progbar.setVisibility(View.GONE);
        }
    }

    private void setDataItem(String result){
        recyclerView.removeAllViews();
        try {
            itemList.clear();
            JSONObject getObjNews = new JSONObject(result);
            JSONArray jAryNews = getObjNews.getJSONArray("products");
            Log.d("COUNT", String.valueOf(jAryNews.length()));
            for (int i = 0; i < jAryNews.length(); i++) {
                JSONObject jObj = jAryNews.getJSONObject(i);
                ModelItem item = new ModelItem();
                item.setId_product(jObj.getString("id"));
                item.setName_Item(jObj.getString("name"));
                item.setPrice(jObj.getString("price"));
                item.setThumbnail(jObj.getString("thumb"));
                item.setDescription(jObj.getString("description"));
                /*
                item.setQuantity(jObj.getInt("quantity"));
                item.setLocation(jObj.getString("location"));
                */
                itemList.add(item);
            }
            recyclerView.setVisibility(View.VISIBLE);

            adapter.notifyDataSetChanged();
        } catch (JSONException e){
            Toast.makeText(getApplicationContext(),"Terjadi Kesalahan..",Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }

    public void InitUINavTabBar()
    {
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_nav_select),
                        Color.parseColor(colors[0]))
                        .title("Home")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_catalog_nav_select),
                        Color.parseColor(colors[0]))
                        .title("Catalog")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {

                switch (navigationTabBar.getModelIndex()){
                    case 0 :
                        break;
                    case 1 :
                        break;
                }
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigationTabBar.deselect();
        new GetDataItem().execute("http://joomla.ternaku.com/?route=feed/rest_api/GetProductsForMobile","");

    }

    private void initUiCollapse() {
        appBarLayout.addOnOffsetChangedListener(this);

        toolbarHeaderView.bindTo("Catalog", "Last seen today at 7.00PM");
        //floatHeaderView.bindTo("Home", "Last seen today at 7.00PM");
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
