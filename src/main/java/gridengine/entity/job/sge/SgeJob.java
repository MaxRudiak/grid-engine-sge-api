package gridengine.entity.job.sge;

import gridengine.entity.util.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * This class represents SGE job as XML.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "job_list")
@XmlAccessorType(XmlAccessType.NONE)
public class SgeJob {

    /**
     * SGE job state.
     */
    @XmlAttribute(name = "state")
    private String state;

    /**
     * SGE job id.
     */
    @XmlElement(name = "JB_job_number")
    private int id;

    /**
     * SGE job priority.
     */
    @XmlElement(name = "JAT_prio")
    private double priority;

    /**
     * SGE job name.
     */
    @XmlElement(name = "JB_name")
    private String name;

    /**
     * SGE job owner.
     */
    @XmlElement(name = "JB_owner")
    private String owner;

    /**
     * The state code {@link gridengine.entity.job.JobState} of the job.
     */
    @XmlElement(name = "state")
    private String stateCode;

    /**
     * SGE job start time
     */
    @XmlElements({
            @XmlElement(name = "JAT_start_time"),
            @XmlElement(name = "JB_submission_time")
    })
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime submissionTime;

    /**
     * Name of the queue in which the job is started.
     */
    @XmlElement(name = "queue_name")
    private String queueName;

    /**
     * The number of slots that the job takes up.
     */
    @XmlElement(name = "slots")
    private int slots;
}
