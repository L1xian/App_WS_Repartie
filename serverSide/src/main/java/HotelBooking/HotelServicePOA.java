package HotelBooking;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class HotelServicePOA extends Servant implements HotelService {

    public abstract Hotel[] searchHotels(String location, String dateRange, int guests);

    public abstract Hotel getHotelDetails(String hotelId) throws HotelNotFound;

    public abstract String[] getAvailableRooms(String hotelId, String dateRange)
            throws HotelNotFound;

    @Override
    public String[] _all_interfaces(POA poa, byte[] objectId) {
        return new String[]{"IDL:HotelBooking/HotelService:1.0"};
    }
}