package vttp.final_project.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vttp.final_project.models.Order;
import vttp.final_project.models.Product;
import vttp.final_project.utils.Constants;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void createNewPurchaseOrder(Order order) {
        jdbcTemplate.update(Constants.SQL_CREATE_ORDER, order.getOrderId(), order.getName(), order.getEmail(),
                order.getAddress(), order.getPhone(), order.getTotalAmount());
        saveItems(order.getOrderId(), order.getCart().getItems());
    }

    private void saveItems(String orderId, List<Product> items) {
        for (Product p : items) {
            jdbcTemplate.update(
                    Constants.SQL_CREATE_ORDER_ITEMS,
                    orderId, p.getProductId(), p.getProductName(), p.getPrice(), p.getDisplayUnit(), p.getImageUrl(),
                    p.getQuantity());
        }
    }
}
