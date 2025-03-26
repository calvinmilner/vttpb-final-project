package vttp.final_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vttp.final_project.models.Order;

import vttp.final_project.repositories.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void createNewPurchaseOrder(Order order) {
        orderRepository.createNewPurchaseOrder(order);
    }
}
