package pl.gda.ug.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gda.ug.domain.Flight;
import pl.gda.ug.supplier.FlightSupplier;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service provides available flight from lowcost & regulatory WS.
 */
@Service
public class FlightProviderService {

    /**
     * List of suppliers.
     */
    private List<FlightSupplier> flightSuppliers;

    /**
     * Constructor with list of flight suppliers.
     * @param flightSuppliers suppliers
     */
    @Autowired
    public FlightProviderService(List<FlightSupplier> flightSuppliers) {
        this.flightSuppliers = flightSuppliers;
    }

    /**
     * List of available flight with the following parameters:.
     *
     * @param destinationCode destination code.
     * @param departureCode   departure code.
     * @param date            start date.
     * @param maxResults      limit of results.
     * @return List of Flight sorted by date
     */
    public List<Flight> getFlightList(
            String destinationCode,
            String departureCode,
            Date date,
            Integer maxResults) {

        List<Flight> flights = Collections.synchronizedList(Lists.newArrayList());

        flights.addAll(flightSuppliers.parallelStream()
                .map(supplier -> supplier.getFlights(destinationCode, departureCode, date, maxResults))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        flights.sort(Comparator.comparing(Flight::getDateTime));

        flights = flights.stream().limit(maxResults).collect(Collectors.toList());

        return flights;
    }

}

