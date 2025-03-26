package vttp.final_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import vttp.final_project.models.Order;
import vttp.final_project.models.Product;

// @Service
// public class GmailEmailService {

//     @Autowired
//     private JavaMailSender mailSender;

//     @Value("${spring.mail.username}")
//     private String mailUsername;

//     public String sendOrderConfirmation(Order order) {

//         StringBuilder sb = new StringBuilder();

//         sb.append("Hi ").append(order.getName()).append(",\n\n");
//         sb.append("Thanks for your purchase! Here are your order details:\n\n");
//         sb.append("Order ID: ").append(order.getOrderId()).append("\n");
//         sb.append("Total Amount: S$").append(order.getTotalAmount()).append("\n");
//         sb.append("Shipping Address: ").append(order.getAddress()).append("\n\n");
//         sb.append("Items:\n");

//         for (Product p : order.getCart().getItems()) {
//             sb.append("- ").append(p.getProductName())
//                     .append(" x ").append(p.getQuantity())
//                     .append(" (S$").append(p.getPrice()).append(" each)\n");
//         }

//         sb.append("\nWe'll notify you once your items are shipped.\n\nCheers,\nRecipe Finder App Team");

//         String content = sb.toString();

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setFrom(mailUsername);
//         message.setTo(order.getEmail());
//         message.setSubject("Order Confirmation for #" + order.getOrderId());
//         message.setText(content);
//         mailSender.send(message);

//         return "Email sent successfully";
//     }
// }
