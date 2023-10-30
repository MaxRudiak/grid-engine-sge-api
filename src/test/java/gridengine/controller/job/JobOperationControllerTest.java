package gridengine.controller.job;

import gridengine.controller.AbstractControllerTest;
import gridengine.entity.Listing;
import gridengine.entity.job.Job;
import gridengine.entity.job.JobState;
import gridengine.service.JobOperationProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@WebMvcTest (JobOperationController.class)
public class JobOperationControllerTest extends AbstractControllerTest {

    private static final String URI = "/jobs";

    @MockBean
    JobOperationProviderService jobOperationProviderService;

    @Test
    public void shouldReturnJsonValueAndOkStatus() throws Exception {
        final Job expectedFirstJob = Job.builder()
                .id(8)
                .name("STDIN")
                .priority(0.555)
                .owner("sgeuser")
                .queueName("main@c242f10e1253")
                .submissionTime(LocalDateTime.parse("2021-07-02T10:46:14"))
                .slots(1)
                .state(JobState.builder()
                        .category(JobState.Category.RUNNING)
                        .state("running")
                        .stateCode("r")
                        .build())
                .build();
        final Job expectedSecondJob = Job.builder()
                .id(2)
                .name("STDIN")
                .priority(0.0)
                .owner("sgeuser")
                .queueName("")
                .submissionTime(LocalDateTime.parse("2021-06-30T17:27:30"))
                .slots(1)
                .state(JobState.builder()
                        .category(JobState.Category.PENDING)
                        .state("pending")
                        .stateCode("qw")
                        .build())
                .build();
        final List<Job> jobList = Arrays.asList(expectedFirstJob, expectedSecondJob);
        final Listing<Job> expectedResult = new Listing<>();
        expectedResult.setList(jobList);
        doReturn(expectedResult).when(jobOperationProviderService).jobListing();

        final MvcResult mvcResult = performMvcRequest(MockMvcRequestBuilders.get(URI));

        verify(jobOperationProviderService).jobListing();
        final String actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResult));
    }
}
