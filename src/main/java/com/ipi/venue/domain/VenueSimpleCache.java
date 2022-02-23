package com.ipi.venue.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Hashtable;

@Component
public class VenueSimpleCache {

    HashMap<String, Venue> cache = new HashMap<>();


    public Venue getVenue(String name) {
        return cache.get(name);
    }

    public void putVenue(Venue venue) {
        cache.put(venue.getName(), venue);
    }
}
