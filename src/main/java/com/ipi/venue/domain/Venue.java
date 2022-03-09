package com.ipi.venue.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class Venue extends RepresentationModel<Venue>{

    private final String name;

    private String address;

    private final UUID id;

    @JsonCreator
    public Venue(@JsonProperty("name") String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getId() {
        return id;
    }
}
