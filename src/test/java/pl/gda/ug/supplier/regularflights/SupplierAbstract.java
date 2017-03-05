package pl.gda.ug.supplier.regularflights;

import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import pl.gda.ug.domain.Flight;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class SupplierAbstract {

    protected Long id;
    protected String destinationCode;
    protected String departureCode;
    protected Date date;
    protected int numberOfTransfers;
    protected int maxResults;
    protected List<Flight> flights;
    protected String price;
    protected String currency;
    protected LocalDateTime localDateTime;
    protected RegularSupplierWS.Money money;
    protected Duration duration;

    protected void dataIsPrepared() {
        id = 123L;
        destinationCode = "destinationCode";
        departureCode = "departureCode";
        date = new Date();
        numberOfTransfers = 12;
        maxResults = 123;
        price = "312";
        currency = "EUR";
        localDateTime = LocalDateTime.now();
        money = new RegularSupplierWS.Money(BigDecimal.valueOf(123.45), Currency.getInstance("EUR"));
        duration = Duration.millis(3600L);
    }


}
