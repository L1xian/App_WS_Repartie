package HotelBooking;

public class BookingServiceHelper {

    public static BookingService narrow(org.omg.CORBA.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BookingService) {
            return (BookingService) obj;
        }
        throw new org.omg.CORBA.BAD_PARAM();
    }
}