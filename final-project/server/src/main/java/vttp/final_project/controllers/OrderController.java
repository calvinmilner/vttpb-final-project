package vttp.final_project.controllers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.final_project.models.Cart;
import vttp.final_project.models.Order;
import vttp.final_project.models.Product;
import vttp.final_project.services.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/api/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postOrder(@RequestBody String payload) {

        System.out.println(payload);
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject j = reader.readObject();
        Order order = new Order();
        order.setName(j.getString("name"));
        order.setEmail(j.getString("email"));
        order.setAddress(j.getString("address"));
        order.setPhone(j.getString("phone"));
        order.setTotalAmount((float) j.getJsonNumber("totalAmount").doubleValue());
        JsonArray jArr = j.getJsonObject("cart").getJsonArray("items");

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < jArr.size(); i++) {
            Product p = new Product();
            JsonObject jObj = jArr.getJsonObject(i);
            p.setProductId(jObj.getInt("productId"));
            p.setProductName(jObj.getString("productName"));
            if (jObj.containsKey("price")) {
                if (jObj.get("price").getValueType() == JsonValue.ValueType.NUMBER) {
                    p.setPrice(jObj.getJsonNumber("price").doubleValue());
                } else {
                    p.setPrice(Double.parseDouble(jObj.getString("price")));
                }
            }
            p.setDisplayUnit(jObj.getString("displayUnit"));
            p.setImageUrl(jObj.getString("imageUrl"));
            p.setQuantity(jObj.getInt("quantity"));
            products.add(p);
        }
        Cart cart = new Cart();
        cart.setItems(products);
        order.setCart(cart);

        try {
            orderService.createNewPurchaseOrder(order);
            JsonObject msg = Json.createObjectBuilder().add("orderId", order.getOrderId()).build();
            return ResponseEntity.ok(msg.toString());
        } catch (Exception ex) {
            JsonObject msg = Json.createObjectBuilder().add("message", ex.getMessage()).build();
            return ResponseEntity.status(400).body(msg.toString());
        }
    }

}
