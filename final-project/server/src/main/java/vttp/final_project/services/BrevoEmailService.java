package vttp.final_project.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;
import vttp.final_project.models.Order;
import vttp.final_project.models.Product;

@Service
public class BrevoEmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    public void sendOrderConfirmation(Order order) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setApiKey(apiKey);

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(senderEmail);
        sender.setName(senderName);

        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(order.getEmail());
        to.setName(order.getName());

        StringBuilder content = new StringBuilder();
        content.append("Hi ").append(order.getName()).append(",<br><br>")
                .append("Thank you for your order!<br><br>")
                .append("Order ID: ").append(order.getOrderId()).append("<br>")
                .append("Total: S$").append(order.getTotalAmount()).append("<br><br>")
                .append("<strong>Items:</strong><br>");
        for (Product p : order.getCart().getItems()) {
            content.append("- ").append(p.getProductName())
                    .append(" x ").append(p.getQuantity())
                    .append(" (S$").append(p.getPrice()).append(" each)<br>");
        }
        content.append("<br>We'll notify you once your items are shipped.<br><br>Cheers,<br>Recipe Finder App Team");

        SendSmtpEmail email = new SendSmtpEmail();
        email.setSender(sender);
        email.setTo(Collections.singletonList(to));
        email.setSubject("Order Confirmation #" + order.getOrderId());
        email.setHtmlContent(content.toString());

        try {
            TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
            CreateSmtpEmail result = apiInstance.sendTransacEmail(email);
            System.out.println("Email sent! Message ID: " + result.getMessageId());
        } catch (ApiException e) {
            System.err.println("Exception when calling Brevo API: " + e.getMessage());
        }
    }
}
