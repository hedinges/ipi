package com.ipi.venue.adapter.in;

import com.ipi.venue.domain.Venue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class VenueController {

    private static final String TEMPLATE = "Venue: %s!";

    public HttpEntity<Venue> venue(
            @RequestParam(value = "name", defaultValue = "myVenue") String name){
        Venue venue = new Venue(String.format(TEMPLATE, name));

        venue.add(linkTo(methodOn(VenueController.class).venue(name)).withSelfRel());

        return new ResponseEntity<>(venue, HttpStatus.OK);
    }
}
