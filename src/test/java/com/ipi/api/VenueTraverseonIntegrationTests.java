package com.ipi.api;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VenueTraverseonIntegrationTests {

    @LocalServerPort
    private int port;

    @Test
    public void envEndPointNotHidden() throws Exception {
        Traverson traverson = new Traverson(new URI("http://localhost:" + this.port + "/venue"),MediaTypes.HAL_JSON);
        String venue = traverson.follow("self").toObject("$.names");
        assertThat(venue).isEqualTo("Venue: myVenue!");
    }


}
