package org.example.corba;

import HotelBooking.*;
import org.omg.CORBA.ORB;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotelBookingImpl extends BookingServicePOA {
    private ORB orb;
    private Map<String, Reservation> reservations = new HashMap<>();

    public HotelBookingImpl(ORB orb) {
        this.orb = orb;
    }

    @Override
    public Reservation makeReservation(String userId, String hotelId, String dateRange)
            throws HotelNotFound, InvalidReservation {

        if (userId == null || userId.isEmpty()) {
            throw new InvalidReservation("User ID cannot be empty");
        }
        if (hotelId == null || hotelId.isEmpty()) {
            throw new HotelNotFound("Hotel ID cannot be empty");
        }
        if (dateRange == null || dateRange.isEmpty()) {
            throw new InvalidReservation("Date range cannot be empty");
        }

        String reservationId = UUID.randomUUID().toString();
        double totalPrice = 299.99; // Dummy price

        Reservation reservation = new Reservation(reservationId, userId, hotelId, totalPrice);
        reservations.put(reservationId, reservation);

        System.out.println("Reservation made: " + reservationId + " for hotel " + hotelId);
        return reservation;
    }

    @Override
    public Reservation getReservation(String reservationId) throws InvalidReservation {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            throw new InvalidReservation("Reservation with ID " + reservationId + " not found");
        }
        return reservation;
    }

    @Override
    public boolean cancelReservation(String reservationId) throws InvalidReservation {
        Reservation reservation = reservations.remove(reservationId);
        if (reservation == null) {
            throw new InvalidReservation("Reservation with ID " + reservationId + " not found");
        }
        System.out.println("Reservation cancelled: " + reservationId);
        return true;
    }
}