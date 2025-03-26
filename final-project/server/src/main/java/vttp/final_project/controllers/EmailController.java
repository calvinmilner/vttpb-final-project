package vttp.final_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vttp.final_project.models.Order;
import vttp.final_project.services.EmailService;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/api/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody Order order) {
        String message = emailService.sendOrderConfirmation(order);
        return ResponseEntity.ok(message);
    }
}
