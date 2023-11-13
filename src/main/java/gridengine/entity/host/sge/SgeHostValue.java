package gridengine.entity.host.sge;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Builder;
import lombok.Data;

/**
 * This class represents xml tag "hostvalue".
 */
@Data
@Builder
@XmlAccessorType(XmlAccessType.NONE)
public class SgeHostValue {

    /**
     * Xml attribute "name" of element "hostvalue".
     */
    @XmlAttribute(name = "hostvalue")
    private String name;
}
