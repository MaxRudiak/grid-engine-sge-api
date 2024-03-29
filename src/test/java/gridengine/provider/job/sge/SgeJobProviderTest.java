package gridengine.provider.job.sge;

import gridengine.cmd.CmdExecutor;
import gridengine.cmd.SimpleCmdExecutor;
import gridengine.entity.CommandResult;
import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.job.Job;
import gridengine.entity.job.JobOptions;
import gridengine.entity.job.JobState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class SgeJobProviderTest {

    private static final String INVALID_XML = "<job_info  xmlns:xsd=" +
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
            "    </job_list>";
    private static final String EMPTY_JOB_LIST = "<?xml version='1.0'?>\n" +
            "<job_info  xmlns:xsd=" +
            "\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qstat/qstat.xsd\">\n" +
            "  <queue_info>\n" +
            "  </queue_info>\n" +
            "  <job_info>\n" +
            "  </job_info>\n" +
            "</job_info>";
    private static final String VALID_XML = "<?xml version='1.0'?>\n" +
            "<job_info  xmlns:xsd=" +
            "\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qstat/qstat.xsd\">\n" +
            "  <queue_info>\n" +
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
            "  </job_info>\n" +
            "</job_info>";

    private final SimpleCmdExecutor mockCmdExecutor = mock(SimpleCmdExecutor.class);
    private final SgeJobProvider sgeJobProvider = new SgeJobProvider(mockCmdExecutor);
    private final CommandResult commandResult = new CommandResult();

    @Test
    public void shouldFailWithInvalidXml() {
        final List<String> jobList = Collections.singletonList(INVALID_XML);
        commandResult.setStdOut(jobList);
        commandResult.setStdErr(new ArrayList<>());
        doReturn(commandResult).when(mockCmdExecutor).execute("qstat", "-xml");

        Assertions.assertEquals(sgeJobProvider.getProviderType(), EngineType.SGE);
        Assertions.assertThrows(IllegalStateException.class, sgeJobProvider::listJobs);
    }

    @Test
    public void shouldNotFailWithEmptyJobList() {
        final List<String> jobList = Collections.singletonList(EMPTY_JOB_LIST);
        commandResult.setStdOut(jobList);
        commandResult.setStdErr(new ArrayList<>());
        doReturn(commandResult).when(mockCmdExecutor).execute("qstat", "-xml");
        final List<Job> result = sgeJobProvider.listJobs().getList();

        Assertions.assertEquals(sgeJobProvider.getProviderType(), EngineType.SGE);
        Assertions.assertEquals(0, result.size());
    }
    @Test
    public void shouldLoadXml() {
        final Job expectedFirstJob = Job.builder()
                .id(7)
                .name("STDIN")
                .priority(0.555)
                .owner("sgeuser")
                .queueName("main@c242f10e1253")
                .submissionTime(LocalDateTime.parse("2021-07-02T10:46:14"))
                .slots(1)
                .state(JobState.builder().category(JobState.Category.RUNNING).state("running").stateCode("r").build())
                .build();
        final Job expectedSecondJob = Job.builder()
                .id(2)
                .name("STDIN")
                .priority(0.0)
                .owner("sgeuser")
                .queueName("")
                .submissionTime(LocalDateTime.parse("2021-06-30T17:27:30"))
                .slots(1)
                .state(JobState.builder().category(JobState.Category.PENDING).state("pending").stateCode("qw").build())
                .build();

        final List<String> jobList = Collections.singletonList(VALID_XML);
        commandResult.setStdOut(jobList);
        commandResult.setStdErr(new ArrayList<>());
        doReturn(commandResult).when(mockCmdExecutor).execute("qstat", "-xml");
        Listing<Job> result = sgeJobProvider.listJobs();

        Assertions.assertEquals(expectedFirstJob, result.getList().get(1));
        Assertions.assertEquals(expectedSecondJob, result.getList().get(0));
    }

    @Test
    public void shouldThrowExceptionBecauseNoCommand() {
        final Throwable thrown = Assertions.assertThrows(IllegalStateException.class,
                () -> sgeJobProvider.runJob(new JobOptions()));
        Assertions.assertNotNull(thrown.getMessage());
    }

    @Test
    public void shouldReturnScheduledJobIndex() {
        final String[] command = new String[]{"qsub", "script.sh"};
        final List<String> submittedJobMessage = Collections.singletonList("Your job 1 (\"script.sh\") has been submitted");
        commandResult.setStdOut(submittedJobMessage);
        commandResult.setStdErr(new ArrayList<>());
        doReturn(commandResult).when(mockCmdExecutor).execute(command);
        final String id = sgeJobProvider.getResultOfExecutedCommand(mockCmdExecutor, command);
        Assertions.assertEquals("1", id);
    }
}
