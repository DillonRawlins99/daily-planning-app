package planning.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

/**
 * Adapter (for JAXB) to convert between the LocalTime and the ISO-8601
 * String representation of the time such as '15:06:43.342'.
 *
 * @author Dillon Rawlins
 */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        return LocalTime.parse(v);
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v.toString();
    }
}