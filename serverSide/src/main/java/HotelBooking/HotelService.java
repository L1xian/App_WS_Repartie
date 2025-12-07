package HotelBooking;

public interface HotelService {

    Hotel[] searchHotels(String location, String dateRange, int guests);

    Hotel getHotelDetails(String hotelId) throws HotelNotFound;

    String[] getAvailableRooms(String hotelId, String dateRange) throws HotelNotFound;
}