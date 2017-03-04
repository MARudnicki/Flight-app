package pl.gda.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.gda.ug.domain.Flight;
import pl.gda.ug.service.FlightProviderService;

import java.util.Date;
import java.util.List;

/**
 * Sample controller for retrieving Flights.
 */
@RestController
public class FlightController {

    /**
     * Service for providing List of flights based on delivered parameters.
     */
    private FlightProviderService flightProviderService;

    @Autowired
    public FlightController(FlightProviderService flightProviderService) {
        this.flightProviderService = flightProviderService;
    }

    /**
     * Retrive flights based on provided parameters.
     *
     * @param destinationCode destination code.
     * @param departureCode   departure code.
     * @param date            date.
     * @param maxResults      max amount of results.
     * @return list of flights in json format.
     */
    @RequestMapping(value = "/flights", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Flight>> getFlights(@RequestParam String destinationCode, @RequestParam String
            departureCode, @DateTimeFormat(pattern = "ddMMyyyy") Date date, @RequestParam int maxResults) {

        return new ResponseEntity<>(flightProviderService.getFlightList(destinationCode, departureCode, date, maxResults),
                HttpStatus.OK);
    }
}
