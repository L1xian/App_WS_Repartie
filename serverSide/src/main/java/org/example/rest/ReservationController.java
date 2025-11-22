package org.example.rest;

import HotelBooking.BookingService;
import HotelBooking.HotelNotFound;
import HotelBooking.InvalidReservation;
import HotelBooking.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private BookingService corbaBookingService;

    @PostMapping
    public ResponseEntity<?> makeReservation(@RequestParam String hotelId,
                                             @RequestParam String userId,
                                             @RequestParam String checkInDate,
                                             @RequestParam String checkOutDate,
                                             @RequestParam long numberOfRooms) {
        try {
            Reservation reservation = corbaBookingService.makeReservation(hotelId, userId, checkInDate, checkOutDate, numberOfRooms);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (HotelNotFound e) {
            return new ResponseEntity<>(e.message, HttpStatus.NOT_FOUND);
        } catch (InvalidReservation e) {
            return new ResponseEntity<>(e.message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable String reservationId) {
        try {
            Reservation reservation = corbaBookingService.getReservation(reservationId);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (InvalidReservation e) {
            return new ResponseEntity<>(e.message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable String reservationId) {
        try {
            corbaBookingService.cancelReservation(reservationId);
            return new ResponseEntity<>("Reservation cancelled successfully", HttpStatus.NO_CONTENT);
        } catch (InvalidReservation e) {
            return new ResponseEntity<>(e.message, HttpStatus.NOT_FOUND);
        }
    }
}
