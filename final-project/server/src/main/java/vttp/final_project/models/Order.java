package vttp.final_project.models;

import java.util.UUID;

public class Order {
    private final String orderId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private float totalAmount;

    private Cart cart = new Cart();

    public Order() {
        orderId = UUID.randomUUID().toString().substring(0, 16).replaceAll("-", "").toUpperCase();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", name=" + name + ", email=" + email + ", address=" + address + ", phone="
                + phone + ", totalAmount=" + totalAmount + ", cart=" + cart + "]";
    }
}
