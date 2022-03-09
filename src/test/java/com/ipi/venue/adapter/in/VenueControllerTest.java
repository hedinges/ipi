package com.ipi.venue.adapter.in;

import com.ipi.venue.domain.Venue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VenueControllerTest {

    @Autowired
    VenueController venueController;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void venue() {
        ResponseEntity<List<Venue>> http = (ResponseEntity<List<Venue>>) venueController.getVenues("testName");
        System.out.println(http);
        assertEquals(HttpStatus.OK,http.getStatusCode());

    }
}