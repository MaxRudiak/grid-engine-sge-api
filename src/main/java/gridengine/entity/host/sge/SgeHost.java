package gridengine.entity.host.sge;

import jakarta.xml.bind.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * This class represents SGE host as xml.
 */
@Data
@Builder
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "host")
public class SgeHost {

    /**
     * Attribute "name".
     */
    @XmlAttribute(name = "name")
    private String name;

    /**
     * List of "hostvalue" tags.
     */
    @XmlElement(name = "hostvalue")
    private List<SgeHostValue> hostValues;
}
