package com.fintech.marketplace;

/**
 * Created by Pandhu on 12/5/16.
 */

public class ModelItem {
    private String Name_Item;
    private String Price;
    private String Thumbnail;

    public ModelItem() {
    }

    public ModelItem(String Name_Item, String Price, String Thumbnail) {
        this.Name_Item = Name_Item;
        this.Price = Price;
        this.Thumbnail = Thumbnail;
    }

    public void setName_Item(String name_Item) {
        Name_Item = name_Item;
    }

    public String getName_Item() {
        return Name_Item;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPrice() {
        return Price;
    }
}
