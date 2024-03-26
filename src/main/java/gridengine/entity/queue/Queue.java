package gridengine.entity.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * A common object that represents a Queue entity.
 */
@Data
@Builder
@AllArgsConstructor
public class Queue {

    /**
     * The name of the cluster queue.
     */
    private String name;

    /**
     * The list of host identifiers.
     */
    private List<String> hostList;

    /**
     * The position for this queue in the scheduling order
     * withing the suitable queues for a job to be dispatched.
     */
    private Integer numberInSchedulingOrder;

    /**
     * Load thresholds.
     */
    private Map<String, Double> loadThresholds;

    /**
     * Load thresholds with the same semantics as that of
     * the {@link Queue#loadThresholds} parameter except that
     * exceeding one of the denoted thresholds initiates suspension
     * of one of multiple jobs in the queue.
     */
    private Map<String, Double> suspendThresholds;

    /**
     * The number of jobs which are suspended/enabled per time
     * interval.
     */
    private Integer numOfSuspendedJobs;

    /**
     * The time interval in which further suspended jobs are
     * suspended.
     */
    private String interval;

    /**
     * The value at which jobs in this queue will be run.
     */
    private Integer jobPriority;

    /**
     * The type of queue.
     */
    private String qtype;

    /**
     * The list of administrator-defined parallel environment
     * names  to  be  associated  with  the  queue.
     */
    private List<String> parallelEnvironmentNames;

    /**
     * The object that provides a number and descriptions of
     * slots (concurrently executing jobs allowed in the queue).
     */
    private SlotsDescription slots;

    /**
     * The list of usernames who are authorized to disable and suspend this queue.
     */
    private List<String> ownerList;

    /**
     * The list of usernames who has access to the queue.
     */
    private List<String> userList;

    /**
     * The absolute path to the base of the temporary directory filesystem.
     */
    private String tmpDir;
}
