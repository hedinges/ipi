package com.ipi.api;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestOperations;


/**
 * https://stackoverflow.com/questions/25901985/difference-between-mockmvc-and-resttemplate-in-integration-tests
 * For now skip RestTemplate integration tests and focus on server side testing with MockMvc
 *
 * Other useful references are:
 * https://medium.com/swlh/https-medium-com-jet-cabral-testing-spring-boot-restful-apis-b84ea031973d
 * https://codingefforts.wordpress.com/2017/06/11/integration-test-resttemplate-vs-mockmvc/
 * https://spring.io/blog/2012/11/12/spring-framework-3-2-rc1-spring-mvc-test-framework
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VenueTraverseonIntegrationTests {

    @LocalServerPort
    private int port;

    @Disabled
    @Test
    public void envEndPointNotHidden() throws Exception {
        Traverson traverson = new Traverson(new URI("http://localhost:" + this.port + "/venue"),MediaTypes.HAL_JSON);
        String venue = traverson.follow("self").toObject("$.names");
        assertThat(venue).isEqualTo("Venue: myVenue!");
    }

    @Disabled
    @Test
    public void createNewVenue() throws Exception {
        Traverson traverson = new Traverson(new URI("http://localhost:" + this.port + "/venue"),MediaTypes.HAL_JSON);

        //traverson.setRestOperations();

        String venue = traverson.follow("self").toObject("$.names");
        assertThat(venue).isEqualTo("Venue: myVenue!");
    }

}
