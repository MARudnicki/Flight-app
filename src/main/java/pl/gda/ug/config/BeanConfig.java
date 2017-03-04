package pl.gda.ug.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gda.ug.supplier.lowcostflights.LowcostFlightSupplierWS;
import pl.gda.ug.supplier.regularflights.RegularFlightSupplierWS;

/**
 * Bean configuration class.
 */
@Configuration
public class BeanConfig {

    /**
     * WS
     * @return LowcostFlightSupplierWS.
     */
    @Bean
    public LowcostFlightSupplierWS lowcostFlightSupplierWS() {
        return new LowcostFlightSupplierWS();
    }

    /**
     * WS
     * @return RegularFlightSupplierWS.
     */
    @Bean
    public RegularFlightSupplierWS regularFlightSupplierWS() {
        return new RegularFlightSupplierWS();
    }
}
