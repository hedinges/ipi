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



    private String venuePayload(Venue venue) throws JsonProcessingException {
        //return String.format("{ name:\"%s\" }\n",venue.getName());

        return mapper.writeValueAsString(venue);
    }
}
