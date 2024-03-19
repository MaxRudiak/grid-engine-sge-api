package gridengine.provider.job;

import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.job.Job;
import gridengine.entity.job.JobOptions;

/**
 * This interface specifies methods for a job provider.
 */
public interface JobProvider {
    Listing<Job> listJobs();

    EngineType getProviderType();

    Job runJob(JobOptions options);
}
