package gridengine.controller.host;

import gridengine.controller.AbstractControllerTest;
import gridengine.entity.Listing;
import gridengine.entity.host.Host;
import gridengine.service.HostOperationProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@WebMvcTest(HostController.class)
public class HostOperationControllerTest extends AbstractControllerTest {

    private static final String URI = "/hosts";

    @MockBean
    private HostOperationProviderService hostOperationProviderService;

    @Test
    public void shouldReturnValueAndStatus() throws Exception {
        final Host expectedHost = Host.builder()
                .hostName("ip-172-31-1-162.eu-central-1.compute.internal")
                .typeOfArchitect("lx-amd64")
                .numOfProcessors(2)
                .load(0.0)
                .memTotal(3600000000L)
                .memUsed(311600000L)
                .totalSwapSpace(0.0)
                .usedSwapSpace(0.0)
                .build();
        final List<Host> hostList = Collections.singletonList(expectedHost);
        final Listing<Host> expectedResult = new Listing<>();
        expectedResult.setList(hostList);
        doReturn(expectedResult).when(hostOperationProviderService).hostListing();

        final MvcResult mvcResult = performMvcRequest(MockMvcRequestBuilders.get(URI));

        verify(hostOperationProviderService).hostListing();
        final String actual = mvcResult.getResponse().getContentAsString();
        assertThat(actual).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResult));
    }
}
