package com.fintech.marketplace;

/**
 * Created by Pandhu on 12/5/16.
 */

public class ModelItem {
    private String id_product;
    private String Name_Item;
    private String Price;
    private String Thumbnail;
    private String Description;
    private int quantity;
    private String location;

    public ModelItem() {
    }

    public ModelItem(String id_product, String Name_Item, String Price, String Thumbnail,String Description, int quantity, String location) {
        this.setId_product(id_product);
        this.Name_Item = Name_Item;
        this.Price = Price;
        this.Thumbnail = Thumbnail;
        this.setDescription(Description);
        this.setQuantity(quantity);
        this.setLocation(location);
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

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
