package HotelBooking;

import org.omg.CORBA.ORB;

public class HotelServiceHelper {

    public static HotelService narrow(org.omg.CORBA.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof HotelService) {
            return (HotelService) obj;
        }
        throw new org.omg.CORBA.BAD_PARAM();
    }

    public static void bind(ORB orb, String name, HotelService servant)
            throws Exception {
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        org.omg.CosNaming.NamingContextExt ncRef =
                org.omg.CosNaming.NamingContextExtHelper.narrow(objRef);

        org.omg.CosNaming.NameComponent path[] =
                ncRef.to_name(name);
        ncRef.rebind(path, (org.omg.CORBA.Object) servant);
    }
}