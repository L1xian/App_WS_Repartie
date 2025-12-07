package HotelBooking;

public interface BookingService {

    Reservation makeReservation(String userId, String hotelId, String dateRange)
            throws HotelNotFound, InvalidReservation;

    boolean cancelReservation(String reservationId) throws InvalidReservation;

    Reservation getReservation(String reservationId) throws InvalidReservation;
}