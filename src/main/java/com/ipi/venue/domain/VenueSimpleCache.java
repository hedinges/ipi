package com.ipi.venue.domain;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

@Component
public class VenueSimpleCache {

    HashMap<String, Venue> cache = new HashMap<>();


    public Venue getVenue(String name) {
        return cache.get(name);
    }

    public void putVenue(Venue venue) {
        cache.put(venue.getName(), venue);
    }

    public List<Venue> list() {
        return cache.values().stream().toList();
    }
}
