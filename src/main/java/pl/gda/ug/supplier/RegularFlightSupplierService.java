package pl.gda.ug.supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gda.ug.domain.Flight;
import pl.gda.ug.supplier.regularflights.RegularFlightSupplierWS;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for calling regularFlightSupplierWS.
 */
@Service
public class RegularFlightSupplierService implements FlightSupplier {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularFlightSupplierService.class);

    /**
     * WS.
     */
    private RegularFlightSupplierWS regularFlightSupplierWS;


    @Autowired
    public RegularFlightSupplierService(RegularFlightSupplierWS regularFlightSupplierWS) {
        this.regularFlightSupplierWS = regularFlightSupplierWS;
    }

    /**
     * Retrieve list of flights based on connections from regular flights supplier ws.
     * @param destCode destination code.
     * @param deptCode departure code
     * @param date date
     * @param maxResults max num of results
     * @return list of flights
     */
    @Override
    public List<Flight> getFlights(String destCode, String deptCode, Date date, Integer maxResults) {

        try {
            return regularFlightSupplierWS.getConnections(destCode, deptCode, date, maxResults).stream().map(this::mapToFlight)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.warn("RegularcostFlightSupplierWS is off", "");

            return Collections.emptyList();
        }
    }

    private Flight mapToFlight(RegularFlightSupplierWS.Connection connection) {
        return Flight.newFlight()
                .id(connection.getId())
                .destinationCode(connection.getDestinationCode())
                .departureCode(connection.getDepartureCode())
                .dateTime(convertJodaTimeToJava8Time(connection.getDateTime()))
                .price(connection.getPrice().getAmount())
                .currency(connection.getPrice().getCurrency())
                .numberOfTransfers(0)
                .duration(Duration.ofSeconds(connection.getDuration().getStandardSeconds()))
                .supplier("RegularFlightSupplierWS")
                .build();
    }

    private LocalDateTime convertJodaTimeToJava8Time(org.joda.time.LocalDateTime jodaDateTime) {

        return LocalDateTime.ofInstant(jodaDateTime.toDate().toInstant(), ZoneId.systemDefault());
    }
}
