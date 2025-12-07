# Projec Overview
This project represents a booking system devided to 
    -serverSide
    -clientSide
used protocols
(SOA)
-REST 
-CORBA
-JNDI

(D.A.R)
-Websocket
-JMS

(BD)
easyphp



## UML Class Diagram

```mermaid
classDiagram
    class Main {
        +main(String[] args)
        +jndiContext() Context
        +connectionFactory() ConnectionFactory
        +jmsTemplate(ConnectionFactory) JmsTemplate
        +jmsListenerContainerFactory(ConnectionFactory) DefaultJmsListenerContainerFactory
        +corbaOrb() ORB
        +corbaBookingService(ORB, Context) BookingService
    }

    class HotelController {
        -corbaBookingService: BookingService
        +searchHotels(String, String, String) List<Hotel>
    }

    class ReservationController {
        -corbaBookingService: BookingService
        +makeReservation(String, String, String, String, long) ResponseEntity
        +getReservation(String) ResponseEntity
        +cancelReservation(String) ResponseEntity
    }

    class JMSMessageListener {
        +receiveMessage(String) void
    }

    class WebSocketConfig {
        +registerWebSocketHandlers(WebSocketHandlerRegistry) void
    }

    class CustomerSupportChatHandler {
        -sessions: List<WebSocketSession>
        +afterConnectionEstablished(WebSocketSession) void
        +handleTextMessage(WebSocketSession, TextMessage) void
        +afterConnectionClosed(WebSocketSession, CloseStatus) void
        -broadcast(String) void
    }

    class CorbaServer {
        +main(String[] args)
    }

    class HotelBookingImpl {
        -orb: ORB
        -hotels: Map<String, Hotel>
        -reservations: Map<String, Reservation>
        +searchHotels(String, String, String) Hotel[]
        +makeReservation(String, String, String, String, long) Reservation
        +getReservation(String) Reservation
        +cancelReservation(String) void
    }

    class BookingService {
        <<interface>>
        +searchHotels(String, String, String) Hotel[]
        +makeReservation(String, String, String, String, long) Reservation
        +getReservation(String) Reservation
        +cancelReservation(String) void
    }

    Main --> HotelController
    Main --> ReservationController
    Main --> JMSMessageListener
    Main --> WebSocketConfig
    Main --> CorbaServer
    
    HotelController ..> BookingService
    ReservationController ..> BookingService
    
    CorbaServer ..> HotelBookingImpl
    HotelBookingImpl ..|> BookingService
    
    WebSocketConfig --> CustomerSupportChatHandler
```
