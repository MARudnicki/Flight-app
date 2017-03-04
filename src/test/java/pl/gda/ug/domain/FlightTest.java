package pl.gda.ug.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class FlightTest {

    private Flight flight;
    private String id;
    private String destinationCode;
    private String departureCode;
    private BigDecimal price;
    private Currency currency;
    private Duration duration;
    private LocalDateTime localDateTime;
    private int numberOfTransfers;
    private String supplier;

    @Test
    public void builderTest() throws Exception {
            Given:
            dataIsPrepared();

            When:
            builderIsCalled();

            Then:
            assertThat(flight.getId(), is(id));
            assertThat(flight.getDestinationCode(), is(destinationCode));
            assertThat(flight.getDepartureCode(), is(departureCode));
            assertThat(flight.getPrice(), is(price));
            assertThat(flight.getCurrency(), is(currency));
            assertThat(flight.getDuration(), is(duration));
            assertThat(flight.getDateTime(), is(localDateTime));
            assertThat(flight.getNumberOfTransfers(), is(numberOfTransfers));
            assertThat(flight.getSupplier(), is(supplier));
    }

    private void dataIsPrepared(){
        id = UUID.randomUUID().toString();
        destinationCode = UUID.randomUUID().toString();
        departureCode = UUID.randomUUID().toString();
        price = BigDecimal.valueOf(12.34);
        currency = Currency.getInstance("EUR");
        duration = Duration.ofSeconds(12);
        localDateTime = LocalDateTime.now();
        numberOfTransfers = 1;
        supplier = "cheapSupplier";

    }

    private void builderIsCalled(){
        flight = Flight.newFlight().id(id)
                .destinationCode(destinationCode)
                .departureCode(departureCode)
                .price(price)
                .currency(currency)
                .duration(duration)
                .dateTime(localDateTime)
                .numberOfTransfers(numberOfTransfers)
                .supplier(supplier)
                .build();
    }
}