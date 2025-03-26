package vttp.final_project.models;

import org.bson.Document;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private String displayUnit;
    private String imageUrl;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(String displayUnit) {
        this.displayUnit = displayUnit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Product fromDocument(Document doc) {
        Product p = new Product();
        p.setProductId(doc.getInteger("id"));
        p.setProductName(doc.getString("name"));
        Object priceObj = doc.get("price");
        if (priceObj instanceof Number) {
            p.setPrice(((Number) priceObj).doubleValue());
        } else {
            p.setPrice(0.0);
        }
        p.setDisplayUnit(String.valueOf(doc.get("display_unit")));
        p.setImageUrl(doc.getString("image_url"));
        return p;
    }

}
