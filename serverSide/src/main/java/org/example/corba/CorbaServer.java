package org.example.corba;

import HotelBooking.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class CorbaServer {
    public static void main(String[] args) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get reference to root POA and activate the POAManager
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Create servant
            HotelBookingImpl bookingServant = new HotelBookingImpl(orb);

            // Get object reference from the servant (THIS IS THE KEY FIX)
            org.omg.CORBA.Object objRef = rootPOA.servant_to_reference(bookingServant);

            // Get the root naming context
            org.omg.CORBA.Object namingObj = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(namingObj);

            // Bind the object reference in naming
            String name = "BookingService";
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, objRef);

            System.out.println("CORBA BookingService ready and waiting...");

            // Wait for invocations from clients
            orb.run();

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}