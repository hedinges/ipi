package com.ipi.venue.adapter.in;

import com.ipi.venue.domain.Venue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class VenueControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void venue() {
        VenueController venueController = new VenueController();
        ResponseEntity http = (ResponseEntity) venueController.getVenue("testName");
        System.out.println(http);
        assertEquals(HttpStatus.OK,http.getStatusCode());

    }
}