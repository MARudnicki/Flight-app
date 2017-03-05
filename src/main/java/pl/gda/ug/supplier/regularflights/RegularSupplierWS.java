package pl.gda.ug.supplier.regularflights;

import org.joda.time.Duration;
import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * A fake web service, that represents a flight supplier.
 */
public class RegularSupplierWS {

    public static final class Connection {
        private final String id;

        private final String destinationCode;

        private final String departureCode;

        private final LocalDateTime dateTime;

        private final Money price;

        private final Duration duration;

        Connection(String id, String destinationCode, String departureCode, LocalDateTime dateTime, Money price, Duration duration) {
            this.id = id;
            this.destinationCode = destinationCode;
            this.departureCode = departureCode;
            this.dateTime = dateTime;
            this.price = price;
            this.duration = duration;
        }

        public String getId() {
            return id;
        }

        public String getDestinationCode() {
            return destinationCode;
        }

        public String getDepartureCode() {
            return departureCode;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public Money getPrice() {
            return price;
        }

        public Duration getDuration() {
            return duration;
        }

    }

    public static final class Money {
        private final BigDecimal amount;
        private final Currency currency;

        Money(BigDecimal amount, Currency currency) {
            this.amount = amount;
            this.currency = currency;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public Currency getCurrency() {
            return currency;
        }
    }

    public List<Connection> getConnections(String destCode, String deptCode, Date date, Integer maxResults) throws IOException {
        if (Boolean.valueOf(System.getProperty("regularIsDown", "false"))) {
            throw new java.net.ConnectException("Connection failed!");
        }

        final Random random = new Random();
        final List<Connection> flights = new ArrayList<Connection>();
        for (int i = 0; i < maxResults; i++) {
            String id = UUID.randomUUID().toString();
            LocalDateTime dateTime = LocalDateTime.fromDateFields(date).withTime(random.nextInt(23), random.nextInt(60), 0, 0);
            Money price = new Money(new BigDecimal(random.nextInt(1000)), Currency.getInstance("EUR"));
            Duration duration = Duration.standardHours(random.nextInt(5));
            flights.add(new Connection(id, destCode, deptCode, dateTime, price, duration));
        }

        try {
//            Thread.sleep(random.nextInt(10)*1000);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        return flights;
    }

}
