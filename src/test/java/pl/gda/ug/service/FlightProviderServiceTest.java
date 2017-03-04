package pl.gda.ug.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gda.ug.domain.Flight;
import pl.gda.ug.supplier.FlightSupplier;
import pl.gda.ug.supplier.LowcostSupplierService;
import pl.gda.ug.supplier.RegularFlightSupplierService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FlightProviderServiceTest {

    private String destinationCode;
    private String departureCode;
    private Date date;
    private int maxResults;
    private List<Flight> flights;
    private Random random = new Random();

    @Mock
    private List<FlightSupplier> flightSuppliers;

    @Mock
    private LowcostSupplierService lowcostSupplierService;

    @Mock
    private RegularFlightSupplierService regularFlightSupplierService;

    @InjectMocks
    private FlightProviderService flightProviderService;

    @Test
    public void getFlightList() throws Exception {

        Given:
        dataIsPrepared();
        when(flightSuppliers.parallelStream()).thenReturn(Lists.newArrayList(lowcostSupplierService,
                regularFlightSupplierService).stream());
        when(lowcostSupplierService.getFlights(anyString(), anyString(), any(Date.class), anyInt())).thenReturn
                (mockFlights(maxResults));
        when(regularFlightSupplierService.getFlights(anyString(), anyString(), any(Date.class), anyInt())).thenReturn
                (mockFlights(maxResults));

        When:
        getFlightIsCalled();

        Then:
        assertThat(flights.size(), is(maxResults));
        assertOrder(flights);

    }

    private void assertOrder(List<Flight> flights) {
        for (int i = 0; i < flights.size() - 1; i++) {
            assertFalse(flights.get(i).getDateTime().isAfter(flights.get(i + 1).getDateTime()));
        }
    }

    private void dataIsPrepared() {
        destinationCode = "destinationCode";
        departureCode = "departureCode";
        date = new Date();
        maxResults = 123;

    }

    private void getFlightIsCalled() {
        flights = flightProviderService.getFlightList(destinationCode, departureCode, date, maxResults);
    }

    private List<Flight> mockFlights(int maxResults) {

        return IntStream.range(0, maxResults)
                .mapToObj(i -> mockFlight())
                .collect(Collectors.toList());
    }

    private Flight mockFlight() {
        return Flight.newFlight().id(UUID.randomUUID().toString())
                .supplier(UUID.randomUUID().toString())
                .numberOfTransfers(random.nextInt())
                .dateTime(getRandomLocalDateTime())
                .currency(Currency.getInstance("EUR"))
                .numberOfTransfers(random.nextInt())
                .destinationCode(UUID.randomUUID().toString())
                .departureCode(UUID.randomUUID().toString())
                .price(BigDecimal.valueOf(random.nextInt()))
                .build();
    }

    private LocalDateTime getRandomLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(random.nextInt()), ZoneId.systemDefault());
    }
}