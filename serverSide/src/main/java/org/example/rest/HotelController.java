package org.example.rest;

import HotelBooking.BookingService;
import HotelBooking.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private BookingService corbaBookingService;

    @GetMapping("/search")
    public List<Hotel> searchHotels(@RequestParam String location,
                                    @RequestParam String checkInDate,
                                    @RequestParam String checkOutDate) {
        System.out.println("REST request: search hotels in " + location);
        Hotel[] hotels = corbaBookingService.searchHotels(location, checkInDate, checkOutDate);
        return Arrays.asList(hotels);
    }
}
