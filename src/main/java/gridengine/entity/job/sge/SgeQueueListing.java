package gridengine.entity.job.sge;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "job_info")
@XmlAccessorType(XmlAccessType.FIELD)
public class SgeQueueListing {

    @XmlElementWrapper(name = "queue_info")
    @XmlElement(name = "job_list")
    private List<SgeJob> sgeQueues;

    @XmlElementWrapper(name = "job_info")
    @XmlElement(name = "job_list")
    private List<SgeJob> sgeJobs;
}
