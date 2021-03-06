package pl.gda.ug.supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gda.ug.domain.Flight;
import pl.gda.ug.supplier.lowcostflights.LowcostSupplierWS;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Supplier service for calling lowcostSupplierWS.
 */
@Service
public class LowcostSupplierService implements FlightSupplier {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularFlightSupplierService.class);

    /**
     * WS.
     */
    private LowcostSupplierWS lowcostSupplierWS;

    @Autowired
    public LowcostSupplierService(LowcostSupplierWS lowcostSupplierWS) {
        this.lowcostSupplierWS = lowcostSupplierWS;
    }

    /**
     * Retrieve list of flights based on flights from ws.
     * @param destCode destination code
     * @param deptCode departure code
     * @param date date
     * @param maxResults max number of results
     * @return list of flights
     */
    @Override
    public List<Flight> getFlights(String destCode, String deptCode, Date date, Integer maxResults) {

        try {
            return lowcostSupplierWS.list(destCode, deptCode, date, maxResults).stream().map(this::mapToFlight)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.warn("Low-costService is off");

            return Collections.emptyList();
        }
    }


    private Flight mapToFlight(LowcostSupplierWS.CheapFlight cheapFlight) {
        return Flight.newFlight()
                .id(String.valueOf(cheapFlight.getId()))
                .destinationCode(cheapFlight.getDestCode())
                .departureCode(cheapFlight.getDeptCode())
                .dateTime(LocalDateTime.ofInstant(cheapFlight.getTime().toInstant(), ZoneId.systemDefault()))
                .price(BigDecimal.valueOf(Double.valueOf(cheapFlight.getPrice())))
                .currency(Currency.getInstance(cheapFlight.getCurrency()))
                .numberOfTransfers(cheapFlight.getNumberOfTransfers())
                .supplier("Low-costSupplierWS")
                .build();
    }
}
