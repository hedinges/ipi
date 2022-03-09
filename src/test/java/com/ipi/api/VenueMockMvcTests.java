package com.ipi.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipi.venue.domain.Venue;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VenueMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    /*
     * Jackson mapper for Object -> JSon conversions
     */
    @Autowired
    ObjectMapper mapper;

    @Test
    public void envEndPointNotHidden() throws Exception  {
        mockMvc.perform(get("/venues").contentType(MediaType.APPLICATION_JSON).param("name","myVenue")).andExpect(status().isOk());
    }

    @Test
    public void listEmptyVenues() throws Exception {
        mockMvc.perform(get("/venues").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(0));
    }


    @Test
    public void createVenue() throws Exception  {
        Venue venue = new Venue("Opera Royal");
        venue.setAddress("BeachFront avenue, 69, Batman turkey");

        System.out.println("Checking for:"+venue.getName());

        mockMvc.perform(post("/venues").contentType(MediaType.APPLICATION_JSON).content(venuePayload(venue))).andExpect(jsonPath("$.name").value("Opera Royal"));

        //how to add body?
    }

    @Test
    public void getVenueByName() throws Exception {
        createVenue();
        mockMvc.perform(get("/venues").contentType(MediaType.APPLICATION_JSON)
                .param("name","Opera Royal"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Opera Royal"));
        mockMvc.perform(get("/venues").contentType(MediaType.APPLICATION_JSON)
                .param("name","Dont Exist"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(0))
                ;

    }

    @Test
    public void getVenueById() throws Exception {
        //Create 4 venues
        //search second one by name
        //get his ID
        //search by id

        Venue venue = new Venue("Opera Royal");
        venue.setAddress("BeachFront avenue, 69, Batman turkey");

        Venue venue2 = new Venue("Le Bilbao Club");
        venue2.setAddress("Avenue des cerisiers, 22, Bruxelles Belgique");

        Venue venue3 = new Venue("The noisy trompet");
        venue3.setAddress("East vilalge, 33, London FreeUk");

        Venue venue4 = new Venue("Le megalophone");
        venue4.setAddress("Rue de l'édition, 44, Charleroi Belgium");

        setUpVenue(venue);
        MvcResult result = setUpVenue(venue2);
        setUpVenue(venue3);
        setUpVenue(venue4);

        String targetId = JsonPath.read(result.getResponse().getContentAsString(),"$.id");

        mockMvc.perform(get("/venues/{id}",targetId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Le Bilbao Club"));

    }

    private MvcResult setUpVenue(Venue venue) throws Exception {
        return mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(venuePayload(venue)))
                .andReturn();
    }


    private String venuePayload(Venue venue) throws JsonProcessingException {
        //return String.format("{ name:\"%s\" }\n",venue.getName());

        return mapper.writeValueAsString(venue);
    }
}
