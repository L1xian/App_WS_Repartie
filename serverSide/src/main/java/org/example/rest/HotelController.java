package org.example.rest;

import HotelBooking.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService corbaHotelService;  // Changed from BookingService to HotelService

    @GetMapping("/search")
    public ResponseEntity<?> searchHotels(@RequestParam String location,
                                          @RequestParam String dateRange,
                                          @RequestParam int guests) {
        try {
            Hotel[] hotels = corbaHotelService.searchHotels(location, dateRange, guests);
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<?> getHotelDetails(@PathVariable String hotelId) {
        try {
            Hotel hotel = corbaHotelService.getHotelDetails(hotelId);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch (HotelNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<?> getAvailableRooms(@PathVariable String hotelId,
                                               @RequestParam String dateRange) {
        try {
            String[] rooms = corbaHotelService.getAvailableRooms(hotelId, dateRange);
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } catch (HotelNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}