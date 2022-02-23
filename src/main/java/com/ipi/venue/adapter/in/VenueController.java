package com.ipi.venue.adapter.in;

import com.ipi.venue.domain.Venue;
import com.ipi.venue.domain.VenueSimpleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class VenueController {

    private static final String TEMPLATE = "Venue: %s!";

    @Autowired
    VenueSimpleCache venues;

    @RequestMapping(value = "/venue", method = RequestMethod.GET)
    public HttpEntity<Venue> getVenue(
            @RequestParam(value = "name", defaultValue = "myVenue") String name){

        Venue venue = venues.getVenue(name);

        if (venue == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(venue, HttpStatus.OK);
    }


    @RequestMapping(value = "/venue", method = RequestMethod.POST)
    public HttpEntity<Venue> createVenue(
            @RequestParam(value = "name", defaultValue = "myVenue") String name){

        Venue venue = venues.getVenue(name);
        if (venue != null) {
            return new ResponseEntity<>(venue, HttpStatus.CONFLICT);
        }

        venue = new Venue(String.format(TEMPLATE, name));
        venue.add(linkTo(methodOn(VenueController.class).getVenue(name)).withSelfRel());

        venues.putVenue(venue);
        return new ResponseEntity<>(venue, HttpStatus.OK);
    }


}
