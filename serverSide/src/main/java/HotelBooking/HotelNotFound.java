package HotelBooking;

public class HotelNotFound extends Exception {
    public String reason;

    public HotelNotFound(String reason) {
        super(reason);
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}