package HotelBooking;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class BookingServicePOA extends Servant implements BookingService {

    public abstract Reservation makeReservation(String userId, String hotelId, String dateRange)
            throws HotelNotFound, InvalidReservation;

    public abstract boolean cancelReservation(String reservationId)
            throws InvalidReservation;

    public abstract Reservation getReservation(String reservationId)
            throws InvalidReservation;

    @Override
    public String[] _all_interfaces(POA poa, byte[] objectId) {
        return new String[]{"IDL:HotelBooking/BookingService:1.0"};
    }
}