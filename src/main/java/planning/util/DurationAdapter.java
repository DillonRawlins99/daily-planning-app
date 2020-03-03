package planning.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Adapter (for JAXB) to convert between Duration and a string representation of
 * the elapsed time duration.
 */
public class DurationAdapter extends XmlAdapter<String, Duration> {

    @Override
    public Duration unmarshal(String v) throws Exception {
        return Duration.parse(v);
    }

    @Override
    public String marshal(Duration v) throws Exception {
        return v.toString();
    }
}
