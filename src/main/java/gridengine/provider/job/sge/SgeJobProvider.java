package gridengine.provider.job.sge;

import gridengine.cmd.SimpleCmdExecutor;
import gridengine.entity.CommandResult;
import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.job.Job;
import gridengine.entity.job.JobState;
import gridengine.entity.job.sge.SgeJob;
import gridengine.entity.job.sge.SgeQueueListing;
import gridengine.provider.job.JobProvider;
import gridengine.provider.utils.JaxbUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SgeJobProvider implements JobProvider {

    private static final String QSTAT_XML = "qstat";
    private static final String TYPE_XML = "-xml";
    private static final String SPACE = "\n";
    private final SimpleCmdExecutor simpleCmdExecutor;

    @Override
    public Listing<Job> listJobs() {
        final CommandResult commandResult = simpleCmdExecutor.execute(QSTAT_XML, TYPE_XML);
        if (commandResult.getExitCode() != 0) {
            throw new IllegalStateException(String.format("Exit code: %s; Error output: %s",
                    commandResult.getExitCode(), String.join(SPACE, commandResult.getStdErr())));
        }
        final SgeQueueListing queueListing = JaxbUtils.unmarshall(String.join(SPACE, commandResult.getStdOut()), SgeQueueListing.class);
        return mapJobs(queueListing);
    }

    @Override
    public EngineType getProviderType() {
        return EngineType.SGE;
    }

    private Listing<Job> mapJobs(final SgeQueueListing sgeQueueListing) {
        final List<SgeJob> sgeJobs = new ArrayList<>();

        if (sgeQueueListing.getSgeJobs() != null) {
            sgeJobs.addAll(sgeQueueListing.getSgeJobs());
        }
        if (sgeQueueListing.getSgeQueues() != null) {
            sgeJobs.addAll(sgeQueueListing.getSgeQueues());
        }

        return new Listing<>(sgeJobs.stream()
                .map(sgeJob -> Job.builder()
                        .id(sgeJob.getId())
                        .priority(sgeJob.getPriority())
                        .name(sgeJob.getName())
                        .owner(sgeJob.getOwner())
                        .state(mapJobState(sgeJob.getState(), sgeJob.getStateCode()))
                        .submissionTime(sgeJob.getSubmissionTime())
                        .queueName(sgeJob.getQueueName())
                        .slots(sgeJob.getSlots())
                        .build())
                .collect(Collectors.toList()));
    }

    private JobState mapJobState(final String state, final String stateCode) {
        final JobState jobState = new JobState();
        switch (state) {
            case "pending":
            case "pending, user hold":
            case "pending, system hold":
            case "pending, user and system hold":
            case "pending, user hold, re-queue":
            case "pending, system hold, re-queue":
            case "pending, user and system hold, re-queue":
                jobState.setCategory(JobState.Category.PENDING);
                break;
            case "running":
            case "transferring":
            case "running, re-submit":
            case "transferring, re-submit":
                jobState.setCategory(JobState.Category.RUNNING);
                break;
            case "ob suspended":
            case "queue suspended":
            case "queue suspended by alarm":
            case "all suspended with re-submit":
                jobState.setCategory(JobState.Category.SUSPENDED);
                break;
            case "all pending states with error":
                jobState.setCategory(JobState.Category.ERROR);
                break;
            case "all running and suspended states with deletion":
                jobState.setCategory(JobState.Category.DELETED);
                break;
            default:
                jobState.setCategory(JobState.Category.UNKNOWN);
                break;
        }
        jobState.setState(state);
        jobState.setStateCode(stateCode);
        return jobState;
        }
}
