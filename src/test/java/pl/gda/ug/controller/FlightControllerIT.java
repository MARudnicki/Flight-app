package pl.gda.ug.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gda.ug.config.Application;
import pl.gda.ug.domain.Flight;
import pl.gda.ug.service.FlightProviderService;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class FlightControllerIT {

    @Mock
    private FlightProviderService flightProviderService;

    @InjectMocks
    private FlightController flightController;

    private String destinationCode;

    private String departureCode;

    private Date date;

    private int maxResults;

    private ResponseEntity<List<Flight>> responseEntity;

    @Test
    public void getFlightsTest() throws Exception {

        Given:
        dataIsPrepared();

        When:
        controllerIsCalled();

        Then:
        verify(flightProviderService, times(1)).getFlightList(destinationCode, departureCode, date, maxResults);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    private void controllerIsCalled() {
        responseEntity = flightController.getFlights(destinationCode, departureCode, date, maxResults);
    }

    private void dataIsPrepared() {
        destinationCode = "destinationCode";
        departureCode = "departureCode";
        date = new Date();
        maxResults = 1;
    }
}