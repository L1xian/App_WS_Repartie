package org.example.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JMSMessageListener {

    @JmsListener(destination = "hotel.booking.queue")
    public void receiveMessage(String message) {
        System.out.println("Received JMS message: " + message);
        // Process the message, e.g., send email confirmation, update database, etc.
    }
}
