package pl.gda.ug.supplier;

import pl.gda.ug.domain.Flight;

import java.util.Date;
import java.util.List;

/**
 * Created by Maciej Rudnicki on 03/03/2017.
 */
public interface FlightSupplier {

    List<Flight> getFlights(String destCode, String deptCode, Date date, Integer maxResults);

}
