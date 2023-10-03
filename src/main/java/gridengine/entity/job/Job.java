package gridengine.entity.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A common job description
 */
@Data
@Builder
@AllArgsConstructor
public class Job {

    /**
     * Job`s id.
     */
    private int id;
    /**
     * Job`s priority.
     */
    private double priority;
    /**
     * Job`s name.
     */
    private String name;
    /**
     * Job`s owner.
     */
    private String owner;
    /**
     * Job`s state.
     */
    private JobState state;
    /**
     * Job`s submission date and time.
     */
    private LocalDateTime submissionTime;
    /**
     * A name of a queue in which current job was submitted.
     */
    private String queueName;
    /**
     * Number of slots that the job takes up.
     */
    private int slots;
}
