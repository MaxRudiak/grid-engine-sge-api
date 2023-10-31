package gridengine.controller.healthcheck;

import gridengine.controller.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest extends AbstractControllerTest {

    private static final String URI = "/check";

    @Test
    public void testCheckHealth() throws Exception {
        final MvcResult mvcResult = performMvcRequest(MockMvcRequestBuilders.get(URI));
        final String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("{\n   \"status\": \"ok\"\n}", content);
    }
}
