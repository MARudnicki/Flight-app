package pl.gda.ug.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gda.ug.supplier.lowcostflights.LowcostSupplierWS;
import pl.gda.ug.supplier.regularflights.RegularSupplierWS;

/**
 * Bean configuration class.
 */
@Configuration
public class BeanConfig {

    /**
     * WS.
     * @return LowcostSupplierWS.
     */
    @Bean
    public LowcostSupplierWS lowcostFlightSupplierWS() {
        return new LowcostSupplierWS();
    }

    /**
     * WS.
     * @return RegularSupplierWS.
     */
    @Bean
    public RegularSupplierWS regularFlightSupplierWS() {
        return new RegularSupplierWS();
    }
}
