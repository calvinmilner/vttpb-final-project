package vttp.final_project.models;

import java.util.LinkedList;
import java.util.List;

public class Cart {
    private List<Product> items = new LinkedList<>();

    public List<Product> getItems() {
        return items;
    }
    
    public void setItems(List<Product> items) {
        this.items = items;
    }
}
