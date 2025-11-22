package org.example.corba;

import HotelBooking.BookingService;
import HotelBooking.BookingServiceHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class CorbaServer {

    public static void main(String[] args) {
        try {
            // 1. Initialize the ORB
            ORB orb = ORB.init(args, null);
            // 2. Get a reference to the POA (RootPOA)
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            // 3. Activate the POA Manager
            rootpoa.the_POAManager().activate();
            // 4. Create the servant (implementation object)
            HotelBookingImpl bookingService = new HotelBookingImpl(orb);
            // 5. Activate the servant and get its object reference
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(bookingService);
            BookingService href = BookingServiceHelper.narrow(ref);
            // 6. Get the Naming Service root context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            // 7. Bind the object reference in the Naming Service
            String name = "BookingService";
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("CORBA Hotel Booking Server ready and waiting...");

            // 8. Wait for incoming requests
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.err);
        }
    }
}
