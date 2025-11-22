# Projec Overview
This project
represents a distributed hotel booking system

It uses 
-REST protocol
-CORBA
-JNDI
it s also split-up to 
    -serverSide
    -clientSide

## Setup and Running the CORBA Server
Follow these steps to set up and run the CORBA server.
### 1. Compile and Install the `corba-server` Module
Navigate to the root directory of the `PROJ` project and compile and install the `corba-server` module. This step will also trigger the `jacorb-maven-plugin` to compile the `HotelBooking.idl` file and generate the necessary Java stubs and skeletons, and install the `corba-server` artifact into your local Maven repository. This artifact is required by the `hotel-booking-client` project.
```bash
cd C:/Users/Dell/Desktop/TP/SOA+DAR/PROJ
mvn clean install
```
### 2. Start the CORBA Naming Service

```bash
jacorb-nameserver
```
**Important**: Ensure the Naming Service is running on `localhost:900` 
or adjust the `corbaOrb` bean configuration in `Main.java`
### 3. Start the CORBA Server
```bash
cd C:/Users/Dell/Desktop/TP/SOA+DAR/PROJ/corba-server
java -jar target/corba-server-1.0-SNAPSHOT.jar -ORBInitRef NameService=corbaloc::localhost:900/NameService
```
You should see "CORBA Hotel Booking Server ready and waiting..."
## Setup and Running the Client-Side (hotel-booking-client)

The client-side application is now a separate project.

### Prerequisites for Client-Side

*   Java Development Kit (JDK) 17 or higher.
*   Maven 3.6.0 or higher.
*   An ActiveMQ broker running (e.g., `tcp://localhost:61616`). You can download and run ActiveMQ from [their official website](http://activemq.apache.org/components/classic/download.html).
*   The `corba-server` artifact must be installed in your local Maven repository (by following step 1 above).

### 1. Compile and Run the `hotel-booking-client`

Navigate to the root directory of the `hotel-booking-client` project (e.g., `C:/Users/Dell/Desktop/TP/SOA+DAR/hotel-booking-client`).

```bash
cd C:/Users/Dell/Desktop/TP/SOA+DAR/hotel-booking-client
mvn clean install
java -jar target/hotel-booking-client-1.0-SNAPSHOT.jar
```
You should see Spring Boot starting up and initializing its components.

### 2. Interact with the REST API

Once all components (CORBA Naming Service, CORBA Server, and `hotel-booking-client`) are running, you can interact with the system using the REST API exposed by the `hotel-booking-client`.

**Search Hotels:**
```bash
curl "http://localhost:8080/api/hotels/search?location=New%20York&checkInDate=2023-12-01&checkOutDate=2023-12-05"
```

**Make a Reservation:**
```bash
curl -X POST "http://localhost:8080/api/reservations?hotelId=H1&userId=user123&checkInDate=2023-12-10&checkOutDate=2023-12-12&numberOfRooms=2"
```

**Get a Reservation:**
(Replace `[reservation-id]` with the ID returned from the make reservation call)
```bash
curl "http://localhost:8080/api/reservations/[reservation-id]"
```

**Cancel a Reservation:**
(Replace `[reservation-id]` with the ID of the reservation to cancel)
```bash
curl -X DELETE "http://localhost:8080/api/reservations/[reservation-id]"
```

### JMS Messaging

Messages related to reservations (e.g., confirmation) will be sent to the `hotel.booking.queue` on the ActiveMQ broker. The `JMSMessageListener` in the `hotel-booking-client` application will consume these messages. You can observe the console output of the `hotel-booking-client` application to see received JMS messages.

### JNDI Usage

JNDI is implicitly used by the CORBA client in `Main.java` (in `hotel-booking-client`) to resolve the `NameService` reference, which then allows it to look up the `BookingService`. While a full JNDI server integration is not explicitly demonstrated with custom bindings beyond CORBA's internal use, the framework is in place.

---
**Note**: This setup assumes all services are running on `localhost`. For a truly distributed deployment, you would need to adjust hostnames and port numbers accordingly in the configurations.
