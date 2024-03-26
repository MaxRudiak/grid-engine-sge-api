package gridengine.entity.queue;

import java.util.Map;

/**
 * This class provides a description of slots object to a {@link Queue} entity.
 */
public class SlotsDescription {
    /**
     * The maximum number of slots (that means the maximum number of
     * concurrently executing jobs allowed in the queue).
     */
    private Integer slots;

    /**
     * A Map that contains host`s name as a key and a number of slots
     * provided by this host as a value.
     */
    private Map<String, Integer> slotsDetails;
}
