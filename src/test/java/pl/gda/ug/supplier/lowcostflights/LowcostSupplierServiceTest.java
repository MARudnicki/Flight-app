package pl.gda.ug.supplier.lowcostflights;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gda.ug.supplier.LowcostSupplierService;
import pl.gda.ug.supplier.regularflights.SupplierAbstract;

import java.io.IOException;
import java.math.BigDecimal;
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
public class LowcostSupplierServiceTest extends SupplierAbstract {

    @Mock
    private LowcostSupplierWS lowcostSupplierWS;

    @InjectMocks
    private LowcostSupplierService lowcostSupplierService;

    @Before
    public void setUp() {
        dataIsPrepared();
    }

    @Test
    public void getFlights() throws Exception {
        Given:
        when(lowcostSupplierWS.list(anyString(), anyString(), any(Date.class), anyInt())).thenReturn
                (Collections.singletonList(new LowcostSupplierWS.CheapFlight(id, destinationCode, departureCode,
                        date, numberOfTransfers, price, currency)));

        When:
        supplierIsCalled();

        Then:
        assertThat(flights.size(), is(1));
        assertThat(flights.get(0).getId(), is(String.valueOf(id)));
        assertThat(flights.get(0).getDestinationCode(), is(destinationCode));
        assertThat(flights.get(0).getDepartureCode(), is(departureCode));
        assertThat(flights.get(0).getNumberOfTransfers(), is(numberOfTransfers));
        assertThat(flights.get(0).getPrice(), is(BigDecimal.valueOf(Double.valueOf(price))));
        assertThat(flights.get(0).getCurrency(), is(Currency.getInstance(currency)));

    }

    @Test
    public void lowcostWSisOff() throws IOException {
        Given:
        when(lowcostSupplierWS.list(anyString(), anyString(), any(Date.class), anyInt())).thenThrow(new
                IOException());

        When:
        supplierIsCalled();

        Then:
        assertThat(flights.size(), is(0));

    }

    private void supplierIsCalled() {
        flights = lowcostSupplierService.getFlights(destinationCode, departureCode, date, maxResults);
    }


}