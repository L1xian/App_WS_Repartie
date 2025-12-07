package org.example.config;

import HotelBooking.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockCorbaConfig {

    @Bean
    public BookingService bookingService() {
        return new BookingService() {
            @Override
            public Reservation makeReservation(String userId, String hotelId, String dateRange)
                    throws HotelNotFound, InvalidReservation {
                return new Reservation("RES-" + System.currentTimeMillis(), userId, hotelId, 299.99);
            }

            @Override
            public boolean cancelReservation(String reservationId) throws InvalidReservation {
                return true;
            }

            @Override
            public Reservation getReservation(String reservationId) throws InvalidReservation {
                return new Reservation(reservationId, "user123", "hotel456", 299.99);
            }
        };
    }

    @Bean
    public HotelService hotelService() {
        return new HotelService() {
            @Override
            public Hotel[] searchHotels(String location, String dateRange, int guests) {
                return new Hotel[]{
                        new Hotel("H1", "Grand Hotel " + location, location),
                        new Hotel("H2", "Luxury Inn " + location, location),
                        new Hotel("H3", "Budget Stay " + location, location)
                };
            }

            @Override
            public Hotel getHotelDetails(String hotelId) throws HotelNotFound {
                if (hotelId.equals("H1")) {
                    return new Hotel("H1", "Grand Hotel", "New York");
                }
                throw new HotelNotFound("Hotel not found: " + hotelId);
            }

            @Override
            public String[] getAvailableRooms(String hotelId, String dateRange) throws HotelNotFound {
                return new String[]{"Room 101", "Room 102", "Room 201", "Suite 301"};
            }
        };
    }
}