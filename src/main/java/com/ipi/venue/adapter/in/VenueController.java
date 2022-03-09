package com.ipi.venue.adapter.in;

import com.ipi.venue.domain.Venue;
import com.ipi.venue.domain.VenueSimpleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class VenueController {

    private static final String TEMPLATE = "Venue: %s!";

    @Autowired
    VenueSimpleCache venues;

    @RequestMapping(value = "/venues", method = RequestMethod.GET)
    public HttpEntity<List<Venue>> getVenues(
            @RequestParam(value = "name"/*, defaultValue = "myVenue"*/,required = false) String name){

        if (null == name) {
            return new ResponseEntity<List<Venue>>(venues.list(), HttpStatus.OK);
        }

        Venue venue = venues.getVenue(name);

        if (venue == null) {
            return new ResponseEntity<List<Venue>>(Collections.EMPTY_LIST, HttpStatus.OK);
        }
        return new ResponseEntity<List<Venue>>(List.of(venue), HttpStatus.OK);
    }

    @PostMapping(value = "/venues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Venue> createVenue(
            @RequestBody() Venue newVenue){

        Venue venue = venues.getVenue(newVenue.getName());
        if (venue != null) {
            return new ResponseEntity<Venue>(venue, HttpStatus.CONFLICT);
        }

        //venue = new Venue(String.format(TEMPLATE, name));
        //todo:Validate venue
        newVenue.add(linkTo(methodOn(VenueController.class).getVenues(newVenue.getName())).withSelfRel());

        venues.putVenue(newVenue);
        return new ResponseEntity<>(newVenue, HttpStatus.OK);
    }


}
