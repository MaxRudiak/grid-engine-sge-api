package gridengine.provider.host.sge;

import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.host.Host;
import gridengine.entity.host.sge.SGEHostProperty;
import gridengine.entity.host.sge.SgeHost;
import gridengine.entity.host.sge.SgeHostListing;
import gridengine.entity.host.sge.SgeHostValue;
import gridengine.provider.host.HostProvider;
import gridengine.provider.utils.JaxbUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import static gridengine.provider.utils.NumberParseUtils.checkString;
import static gridengine.provider.utils.NumberParseUtils.stringToDouble;
import static gridengine.provider.utils.NumberParseUtils.stringToInt;
import static gridengine.provider.utils.NumberParseUtils.stringToLong;

/**
 * Implementation of host provider for Sun Grid Engine
 */
@Service
public class SgeHostProvider implements HostProvider  {

    private static String QHOST_XML =
            "<?xml version='1.0'?>\n" +
            "<qhost xmlns:xsd=\"http://arc.liv.ac.uk/repos/darcs/sge/source/dist/util/resources/schemas/qhost/qhost.xsd\">\n" +
            "<host name='global'>\n" +
            "<hostvalue name='arch_string'>-</hostvalue>\n" +
            "<hostvalue name='num_proc'>-</hostvalue>\n" +
            "<hostvalue name='load_avg'>-</hostvalue>\n" +
            "<hostvalue name='mem_total'>-</hostvalue>\n" +
            "<hostvalue name='mem_used'>-</hostvalue>\n" +
            "<hostvalue name='swap_total'>-</hostvalue>\n" +
            "<hostvalue name='swap_used'>-</hostvalue>\n" +
            "</host>\n" +
            "<host name='ip-172-31-1-162.eu-central-1.compute.internal'>\n" +
            "<hostvalue name='arch_string'>lx-amd64</hostvalue>\n" +
            "<hostvalue name='num_proc'>2</hostvalue>\n" +
            "<hostvalue name='load_avg'>0.00</hostvalue>\n" +
            "<hostvalue name='mem_total'>3.6G</hostvalue>\n" +
            "<hostvalue name='mem_used'>311.6M</hostvalue>\n" +
            "<hostvalue name='swap_total'>0.0</hostvalue>\n" +
            "<hostvalue name='swap_used'>0.0</hostvalue>\n" +
            "</host>\n" +
            "</qhost>";

    @Override
    public Listing<Host> listHosts() {
        return getSgeHosts(JaxbUtils.unmarshall(QHOST_XML, SgeHostListing.class));
    }
    final public Listing<Host> getSgeHosts(SgeHostListing sgeHostListing) {
        return new Listing<>(
                CollectionUtils.emptyIfNull(sgeHostListing.getSgeHosts()).stream()
                        .map(this::fillElements).collect(Collectors.toList())
        );
    }

    private Host fillElements(final SgeHost sgeHost){
        final Map<SGEHostProperty, String> hostPropertyToName = sgeHost
                .getHostValues()
                .stream()
                .collect(Collectors.toMap(SgeHostValue::getName, SgeHostValue::getValue));
        return Host.builder()
                .hostName(sgeHost.getName())
                .typeOfArchitect(checkString(hostPropertyToName.get(SGEHostProperty.TYPE_OF_ARCHITECT)))
                .numOfProcessors(stringToInt(hostPropertyToName.get(SGEHostProperty.NUM_OF_PROCESSORS)))
                .load(stringToDouble(hostPropertyToName.get(SGEHostProperty.LOAD)))
                .memTotal(stringToLong(hostPropertyToName.get(SGEHostProperty.MEM_TOTAL)))
                .memUsed(stringToLong(hostPropertyToName.get(SGEHostProperty.MEM_USED)))
                .totalSwapSpace(stringToDouble(hostPropertyToName.get(SGEHostProperty.TOTAL_SWAP_SPACE)))
                .usedSwapSpace(stringToDouble(hostPropertyToName.get(SGEHostProperty.USED_SWAP_SPACE)))
                .build();
    }

    /**
     * This method tells what grid engine is used.
     *
     * @return Type of grid engine
     * @see EngineType
     */
    @Override
    public EngineType getProviderType() {
        return EngineType.SGE;
    }

    void setXml(String qXml) {
        QHOST_XML = qXml;
    }
}
