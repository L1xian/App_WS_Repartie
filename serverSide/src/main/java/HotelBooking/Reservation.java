package HotelBooking;

public class Reservation {
    public String id;
    public String userId;
    public String hotelId;
    public double totalPrice;

    public Reservation() {}

    public Reservation(String id, String userId, String hotelId, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
        this.totalPrice = totalPrice;
    }
}