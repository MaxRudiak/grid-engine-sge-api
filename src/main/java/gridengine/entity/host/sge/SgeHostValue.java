package gridengine.entity.host.sge;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents xml tag "hostvalue".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name="hostvalue")
@XmlAccessorType(XmlAccessType.FIELD)
public class SgeHostValue {

    /**
     * Xml attribute "name" of element "hostvalue".
     */
    @XmlAttribute(name = "name")
    private SGEHostProperty name;
    @XmlValue
    private String value;
}
