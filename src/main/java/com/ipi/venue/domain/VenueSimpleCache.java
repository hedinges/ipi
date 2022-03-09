package com.ipi.venue.domain;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Venue> getVenueById(UUID id) {
        return cache.values().stream()
                .filter(venue -> venue.getId().equals(id))
                .collect(Collectors.toList());
    }

}
