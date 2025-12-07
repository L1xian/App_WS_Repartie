package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

@SpringBootApplication
@EnableJms
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // JNDI Configuration (for demonstration, a simple file-based context or similar)
    @Bean
    public Context jndiContext() throws NamingException {
        return new InitialContext();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new org.apache.activemq.ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // Configure concurrency, error handling, etc.
        return factory;
    }

    // CORBA BEANS COMMENTED OUT - Using MockCorbaConfig instead for Java 17 compatibility
    // Uncomment these when you want to connect to a real CORBA server

    /*
    @Bean
    public org.omg.CORBA.ORB corbaOrb() {
        return org.omg.CORBA.ORB.init((String[])null, null);
    }

    @Bean
    public HotelBooking.BookingService corbaBookingService(org.omg.CORBA.ORB orb, Context jndiContext) {
        try {
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt ncRef = org.omg.CosNaming.NamingContextExtHelper.narrow(objRef);
            String name = "BookingService"; // The name under which the CORBA server registers its service
            org.omg.CORBA.Object corbaObject = ncRef.resolve_str(name);

            return HotelBooking.BookingServiceHelper.narrow(corbaObject);

        } catch (Exception e) {
            System.err.println("ERROR: Could not initialize CORBA BookingService: " + e.getMessage());
            e.printStackTrace();
            // Depending on the application, you might want to exit or provide a fallback
            return null;
        }
    }
    */
}