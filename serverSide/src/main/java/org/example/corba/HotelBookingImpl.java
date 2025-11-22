package org.example.corba;

import HotelBooking.*;
import org.omg.CORBA.ORB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotelBookingImpl extends BookingServicePOA {
    private ORB orb;
    private Map<String, Hotel> hotels = new HashMap<>();
    private Map<String, Reservation> reservations = new HashMap<>();

    public HotelBookingImpl(ORB orb) {
        this.orb = orb;
        // Initialize with some dummy data
        hotels.put("H1", new Hotel("H1", "Grand Hyatt", "New York", 10));
        hotels.put("H2", new Hotel("H2", "Hilton", "Los Angeles", 5));
    }

    @Override
    public Hotel[] searchHotels(String location, String checkInDate, String checkOutDate) {
        System.out.println("Searching hotels in " + location);
        return hotels.values().stream()
                .filter(h -> h.location.equalsIgnoreCase(location) && h.availableRooms > 0)
                .toArray(Hotel[]::new);
    }

    @Override
    public Reservation makeReservation(String hotelId, String userId, String checkInDate, String checkOutDate, long numberOfRooms) throws HotelNotFound, InvalidReservation {
        Hotel hotel = hotels.get(hotelId);
        if (hotel == null) {
            throw new HotelNotFound("Hotel with ID " + hotelId + " not found.");
        }
        if (hotel.availableRooms < numberOfRooms) {
            throw new InvalidReservation("Not enough rooms available at " + hotel.name);
        }
        if (numberOfRooms <= 0) {
            throw new InvalidReservation("Number of rooms must be positive.");
        }

        hotel.availableRooms -= numberOfRooms;
        hotels.put(hotelId, hotel); // Update hotel availability

        String reservationId = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(reservationId, hotelId, userId, checkInDate, checkOutDate, numberOfRooms);
        reservations.put(reservationId, reservation);

        System.out.println("Reservation made: " + reservationId + " for hotel " + hotel.name);
        return reservation;
    }

    @Override
    public Reservation getReservation(String reservationId) throws InvalidReservation {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            throw new InvalidReservation("Reservation with ID " + reservationId + " not found.");
        }
        return reservation;
    }

    @Override
    public void cancelReservation(String reservationId) throws InvalidReservation {
        Reservation reservation = reservations.remove(reservationId);
        if (reservation == null) {
            throw new InvalidReservation("Reservation with ID " + reservationId + " not found.");
        }

        Hotel hotel = hotels.get(reservation.hotelId);
        if (hotel != null) {
            hotel.availableRooms += reservation.numberOfRooms;
            hotels.put(hotel.id, hotel); // Update hotel availability
        }
        System.out.println("Reservation cancelled: " + reservationId);
    }
}
