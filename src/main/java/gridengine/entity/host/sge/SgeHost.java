package gridengine.entity.host.sge;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class represents SGE host as xml.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement(name = "host")
@XmlAccessorType(XmlAccessType.FIELD)
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
