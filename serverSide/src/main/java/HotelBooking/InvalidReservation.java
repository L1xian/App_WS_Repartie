package HotelBooking;

public class InvalidReservation extends Exception {
    public String reason;

    public InvalidReservation(String reason) {
        super(reason);
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}