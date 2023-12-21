package gridengine.provider.host.sge;

import gridengine.entity.host.Host;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SgeHostProviderTest {

    private static final String CORRECT_XML = "<?xml version='1.0'?>"
            + "<qhost xmlns:xsd=\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qhost/qhost.xsd\">\n"
            + "<host name='ip-172-31-1-162.eu-central-1.compute.internal'>\n"
            + "<hostvalue name='arch_string'>lx-amd64</hostvalue>\n"
            + "<hostvalue name='num_proc'>2</hostvalue>\n"
            + "<hostvalue name='load_avg'>0.00</hostvalue>\n"
            + "<hostvalue name='mem_total'>3.6G</hostvalue>\n"
            + "<hostvalue name='mem_used'>311.6M</hostvalue>\n"
            + "<hostvalue name='swap_total'>0.0</hostvalue>\n"
            + "<hostvalue name='swap_used'>0.0</hostvalue>\n"
            + "</host>\n"
            + "</qhost>";

    private static final String EMPTY_XML = "<?xml version='1.0'?>"
            + "<qhost xmlns:xsd=\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qhost/qhost.xsd\">\n"
            + "<host name=''>\n"
            + "<hostvalue/>\n"
            + " <hostvalue/>\n"
            + "<hostvalue/>\n"
            + "<hostvalue/>\n"
            + "<hostvalue/>\n"
            + "<hostvalue/>\n"
            + "<hostvalue/>\n"
            + "</host>\n"
            + "</qhost>";

    private static final String INCORRECT_XML = "<?xml version='1.0'?>"
            + "<qhost xmlns:xsd=\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qhost/qhost.xsd\">\n"
            + "<host name='test'>\n" + "<hostvalue name='arch_string'>dv</hostvalue>\n"
            + "<hostvalue name='num_proc'>sff </hostvalue>\n"
            + "<hostvalue name='load_avg'>jk,o</hostvalue>\n"
            + "<hostvalue name='mem_total'>j,olo</hostvalue>\n"
            + "<hostvalue name='mem_used'>yui</hostvalue>\n"
            + "<hostvalue name='swap_total'>jmnb</hostvalue>\n"
            + "<hostvalue name='swap_used'>olo,</hostvalue>\n"
            + "</host>\n"
            + "</qhost>";

    private final SgeHostProvider sgeHostProvider = new SgeHostProvider();

    @Test
    public void shouldReturnCorrectXml() {
        sgeHostProvider.setXml(CORRECT_XML);

        final List<Host> listOfXml = sgeHostProvider.listHosts().getList();

        final Host expectedHost = Host.builder().
                hostName("ip-172-31-1-162.eu-central-1.compute.internal").
                typeOfArchitect("lx-amd64")
                .numOfProcessors(2)
                .load(0.0)
                .memTotal(3600000000L)
                .memUsed(311600000L)
                .totalSwapSpace(0.0)
                .usedSwapSpace(0.0)
                .build();

        Assertions.assertEquals(expectedHost, listOfXml.get(0));
    }

    @Test
    public void shouldThrowExceptionWithEmptyInput() {
        sgeHostProvider.setXml(EMPTY_XML);

        Assertions.assertThrows(IllegalStateException.class, sgeHostProvider::listHosts);
    }

    @Test
    public void shouldThrowExceptionWithIncorrectInput() {
        sgeHostProvider.setXml(INCORRECT_XML);

        Assertions.assertThrows(IllegalArgumentException.class, sgeHostProvider::listHosts);
    }
}
