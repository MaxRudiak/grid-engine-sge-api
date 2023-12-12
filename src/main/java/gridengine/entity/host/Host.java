package gridengine.entity.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * A common host description.
 */
@Data
@Builder
@AllArgsConstructor
public class Host {

    /**
     * Host`s name.
     */
    private String hostName;

    /**
     * A type of system architecture.
     */
    private String typeOfArchitect;

    /**
     * Number of processors provided by host.
     */
    private Integer numOfProcessors;

    /**
     * A medium time average OS runs queue length.
     */
    private Double load;

    /**
     * Total amount of memory.
     */
    private Long memTotal;

    /**
     * Total amount of use memory.
     */
    private Long memUsed;

    /**
     * Total amount of swap space
     * (memory in form of a partition or a file).
     */
    private Double totalSwapSpace;

    /**
     * Total Amount of used swap space.
     */
    private Double usedSwapSpace;
}
