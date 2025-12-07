package org.rest;

import HotelBooking.*;
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
    public ResponseEntity<?> makeReservation(@RequestParam String userId,
                                             @RequestParam String hotelId,
                                             @RequestParam String dateRange) {
        try {
            Reservation reservation = corbaBookingService.makeReservation(userId, hotelId, dateRange);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (HotelNotFound e) {
            return new ResponseEntity<>(e.reason, HttpStatus.NOT_FOUND);
        } catch (InvalidReservation e) {
            return new ResponseEntity<>(e.reason, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable String reservationId) {
        try {
            Reservation reservation = corbaBookingService.getReservation(reservationId);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (InvalidReservation e) {
            return new ResponseEntity<>(e.reason, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable String reservationId) {
        try {
            boolean cancelled = corbaBookingService.cancelReservation(reservationId);
            if (cancelled) {
                return new ResponseEntity<>("Reservation cancelled", HttpStatus.OK);
            }
            return new ResponseEntity<>("Cancellation failed", HttpStatus.BAD_REQUEST);
        } catch (InvalidReservation e) {
            return new ResponseEntity<>(e.reason, HttpStatus.NOT_FOUND);
        }
    }
}