package gridengine.controller.job;

import gridengine.entity.Listing;
import gridengine.entity.job.Job;
import gridengine.entity.job.JobOptions;
import gridengine.service.JobOperationProviderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for job management operations.
 */
@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobOperationController {

    private final JobOperationProviderService providerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lists all jobs in queues",
    notes = "Return list of jobs in queues, both running and waiting to run.",
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Listing<Job> listJobs() {
        return providerService.jobListing();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Submits a job into a cluster",
    notes = "Tries to add a job to a queue. Returns the index of the job, if successfully.")
    public String runJob(@RequestBody JobOptions options) {
        return providerService.runJob(options);
    }
}
