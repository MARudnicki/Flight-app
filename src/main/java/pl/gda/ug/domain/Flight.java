package pl.gda.ug.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Domain containing single flight details.
 */
public class Flight {

    /**
     * Flight id.
     */
    private String id;

    /**
     * Destination code.
     */
    private String destinationCode;

    /**
     * Departure code.
     */
    private String departureCode;

    /**
     * Ticket price.
     */
    private BigDecimal price;

    /**
     * Price currency.
     */
    private Currency currency;

    /**
     * Flight duration.
     */
    private Duration duration;

    /**
     * Flight start time.
     */
    private LocalDateTime dateTime;

    /**
     * Number of transfers during flight.
     */
    private int numberOfTransfers;

    /**
     * Supplier name.
     */
    private String supplier;

    /**
     * Contructor - with builder
     * @param builder builder.
     */
    private Flight(Builder builder) {
        this.id = builder.id;
        this.destinationCode = builder.destinationCode;
        this.departureCode = builder.departureCode;
        this.price = builder.price;
        this.currency = builder.currency;
        this.duration = builder.duration;
        this.dateTime = builder.dateTime;
        this.numberOfTransfers = builder.numberOfTransfers;
        this.duration = builder.duration;
        this.supplier = builder.supplier;
    }

    /**
     * Calling for builder.
     * @return builder instance.
     */
    public static Builder newFlight() {
        return new Builder();
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

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getNumberOfTransfers() {
        return numberOfTransfers;
    }

    public String getSupplier() {
        return supplier;
    }

    /**
     * Builder pattern.
     */
    public static final class Builder {
        private String id;
        private String destinationCode;
        private String departureCode;
        private BigDecimal price;
        private Currency currency;
        private Duration duration;
        private LocalDateTime dateTime;
        private int numberOfTransfers;
        private String supplier;

        private Builder() {
        }

        public Flight build() {
            return new Flight(this);
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder destinationCode(String destinationCode) {
            this.destinationCode = destinationCode;
            return this;
        }

        public Builder departureCode(String departureCode) {
            this.departureCode = departureCode;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder numberOfTransfers(int numberOfTransfers) {
            this.numberOfTransfers = numberOfTransfers;
            return this;
        }

        public Builder supplier(String supplier) {
            this.supplier = supplier;
            return this;
        }
    }
}

