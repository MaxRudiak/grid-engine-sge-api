package gridengine.entity.host.sge;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class represents list of hosts as xml.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "qhost")
@XmlAccessorType(XmlAccessType.NONE)
public class SgeHostListing {

    @XmlElement(name = "host")
    private List<SgeHost> sgeHosts;
}
