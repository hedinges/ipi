package com.ipi.venue.adapter.in;

import com.ipi.venue.domain.Venue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VenueTest {

    @DisplayName("Test Venue basics Venue.getName")
    @Test
    void getName() {
        Venue venue = new Venue("OperaRoyal");
        assertEquals("OperaRoyal", venue.getName());
    }
}