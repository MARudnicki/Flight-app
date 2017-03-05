package pl.gda.ug.supplier.regularflights;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gda.ug.supplier.RegularFlightSupplierService;

import java.io.IOException;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RegularFlightSupplierServiceTest extends SupplierAbstract {

    @Mock
    private RegularSupplierWS regularSupplierWS;

    @InjectMocks
    private RegularFlightSupplierService regularFlightSupplierService;

    @Test
    public void getFlights() throws Exception {
        Given:
        dataIsPrepared();
        when(regularSupplierWS.getConnections(anyString(), anyString(), any(Date.class), anyInt())).thenReturn
                (Collections.singletonList(new RegularSupplierWS.Connection(String.valueOf(id), destinationCode,
                        departureCode, localDateTime, money, duration)));


        When:
        supplierIsCalled();

        Then:
        assertThat(flights.size(), is(1));
        assertThat(flights.get(0).getId(), is(String.valueOf(id)));
        assertThat(flights.get(0).getDestinationCode(), is(destinationCode));
        assertThat(flights.get(0).getDepartureCode(), is(departureCode));
        assertThat(flights.get(0).getNumberOfTransfers(), is(0));
        assertThat(flights.get(0).getPrice(), is(money.getAmount()));
        assertThat(flights.get(0).getCurrency(), is(Currency.getInstance(currency)));

    }

    @Test
    public void regularSupplierIsOff() throws IOException {
        Given:
        when(regularSupplierWS.getConnections(anyString(), anyString(), any(Date.class), anyInt())).thenThrow(new
                IOException());

        When:
        supplierIsCalled();

        Then:
        assertThat(flights.size(), is(0));

    }

    private void supplierIsCalled() {
        flights = regularFlightSupplierService.getFlights(destinationCode, departureCode, date, maxResults);
    }


}