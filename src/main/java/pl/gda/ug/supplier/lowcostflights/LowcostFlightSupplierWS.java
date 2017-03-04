package pl.gda.ug.supplier.lowcostflights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDateTime;

/**
 * A fake web service, that represents a flight supplier.
 */
public class LowcostFlightSupplierWS {

    public static final class CheapFlight {
        private final Long id;

        private final String destCode;
        private final String deptCode;

        private final Date time;

        private final Integer numberOfTransfers;

        private final String price;
        private final String currency;

        CheapFlight(Long id, String destCode, String deptCode, Date time, Integer numberOfTransfers, String price, String currency) {
            this.id = id;
            this.destCode = destCode;
            this.deptCode = deptCode;
            this.time = time;
            this.numberOfTransfers = numberOfTransfers;
            this.price = price;
            this.currency = currency;
        }

        public Long getId() {
            return id;
        }

        public String getDestCode() {
            return destCode;
        }

        public String getDeptCode() {
            return deptCode;
        }

        public Date getTime() {
            return time;
        }

        public Integer getNumberOfTransfers() {
            return numberOfTransfers;
        }

        public String getPrice() {
            return price;
        }

        public String getCurrency() {
            return currency;
        }

    }

    public List<CheapFlight> list(String destCode, String deptCode, Date date, Integer maxResults) throws IOException {
        if (Boolean.valueOf(System.getProperty("lowcostIsDown", "false"))) {
            throw new java.net.ConnectException("Connection failed!");
        }

        final Random random = new Random();
        final List<CheapFlight> flights = new ArrayList<CheapFlight>();
        for (int i=0; i < maxResults; i++) {
            Long id = Long.valueOf(i);
            Date time = LocalDateTime.fromDateFields(date).withTime(random.nextInt(23), random.nextInt(60), 0, 0).toDate();
            Integer numberOfTransfers = random.nextInt(3);
            String price = random.nextInt(1000) + "." + random.nextInt(100);
            String currency = "EUR";
            flights.add(new CheapFlight(id, destCode, deptCode, time, numberOfTransfers, price, currency));
        }

        try {
//            Thread.sleep(random.nextInt(10)*1000);
            Thread.sleep(5000);
        } catch (InterruptedException e) {}

        return flights;
    }
}
