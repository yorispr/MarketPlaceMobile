package com.fintech.marketplace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Pandhu on 12/5/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<ModelItem> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView holder_list_namaitem, holder_list_price;
        public ImageView holder_list_thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            holder_list_namaitem = (TextView) view.findViewById(R.id.holder_list_namaitem);
            holder_list_price = (TextView) view.findViewById(R.id.holder_list_price);
            holder_list_thumbnail = (ImageView) view.findViewById(R.id.holder_list_thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public ItemAdapter(Context mContext, List<ModelItem> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_list_produk, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ModelItem item = itemList.get(position);
        new DownloadImageTask(holder.holder_list_thumbnail).execute("http://"+item.getThumbnail().replaceAll(" ","%20"));
        holder.holder_list_namaitem.setText(item.getName_Item());
        holder.holder_list_price.setText(item.getPrice());

        // loading album cover using Glide library
        Glide.with(mContext).load(item.getThumbnail()).into(holder.holder_list_thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_bintang:
                    Toast.makeText(mContext, "Bintang Berhasil Diberikan", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_beli:
                    Toast.makeText(mContext, "Barang Dibeli", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView holder_list_thumbnail;
        TextView  holder_list_price;
        TextView  holder_list_namaitem;
    }
}
