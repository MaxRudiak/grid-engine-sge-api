package gridengine.provider.job.sge;

import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.job.Job;
import gridengine.entity.job.JobState;
import gridengine.entity.job.sge.SgeJob;
import gridengine.entity.job.sge.SgeQueueListing;
import gridengine.provider.job.JobProvider;
import gridengine.provider.utils.JaxbUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SgeJobProvider implements JobProvider {

    private String QSTAT_XML =
            "<?xml version='1.0'?>\n" +
                    "<job_info  xmlns:xsd=" +
                    "\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qstat/qstat.xsd\">\n" +
                    "  <queue_info>\n" +
                    "  <job_list state=\"running\">\n" +
                    "      <JB_job_number>8</JB_job_number>\n" +
                    "      <JAT_prio>0.55500</JAT_prio>\n" +
                    "      <JB_name>STDIN</JB_name>\n" +
                    "      <JB_owner>sgeuser</JB_owner>\n" +
                    "      <state>r</state>\n" +
                    "      <JAT_start_time>2021-07-02T10:46:14</JAT_start_time>\n" +
                    "      <queue_name>main@c242f10e1253</queue_name>\n" +
                    "      <slots>1</slots>\n" +
                    "    </job_list>" +
                    "    <job_list state=\"running\">\n" +
                    "      <JB_job_number>7</JB_job_number>\n" +
                    "      <JAT_prio>0.55500</JAT_prio>\n" +
                    "      <JB_name>STDIN</JB_name>\n" +
                    "      <JB_owner>sgeuser</JB_owner>\n" +
                    "      <state>r</state>\n" +
                    "      <JAT_start_time>2021-07-02T10:46:14</JAT_start_time>\n" +
                    "      <queue_name>main@c242f10e1253</queue_name>\n" +
                    "      <slots>1</slots>\n" +
                    "    </job_list>\n" +
                    "  </queue_info>\n" +
                    "  <job_info>\n" +
                    "    <job_list state=\"pending\">\n" +
                    "      <JB_job_number>2</JB_job_number>\n" +
                    "      <JAT_prio>0.00000</JAT_prio>\n" +
                    "      <JB_name>STDIN</JB_name>\n" +
                    "      <JB_owner>sgeuser</JB_owner>\n" +
                    "      <state>qw</state>\n" +
                    "      <JB_submission_time>2021-06-30T17:27:30</JB_submission_time>\n" +
                    "      <queue_name></queue_name>\n" +
                    "      <slots>1</slots>\n" +
                    "    </job_list>\n" +
                    "    <job_list state=\"pending\">\n" +
                    "      <JB_job_number>9</JB_job_number>\n" +
                    "      <JAT_prio>0.00000</JAT_prio>\n" +
                    "      <JB_name>STDIN</JB_name>\n" +
                    "      <JB_owner>sgeuser</JB_owner>\n" +
                    "      <state>qw</state>\n" +
                    "      <JB_submission_time>2021-06-30T17:27:30</JB_submission_time>\n" +
                    "      <queue_name></queue_name>\n" +
                    "      <slots>1</slots>\n" +
                    "    </job_list>\n" +
                    "  </job_info>\n" +
                    "</job_info>";
    @Override
    public Listing<Job> listJobs() {
        final SgeQueueListing queueListing = JaxbUtils.unmarshall(QSTAT_XML, SgeQueueListing.class);
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
