package gridengine.service;

import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.host.Host;
import gridengine.provider.host.HostProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class defines which grid engine should be used and calls appropriate methods.
 */
@Service
public class HostOperationProviderService {
    private final EngineType engineType;
    /**
     * Collection of providers by engine type.
     */
    private Map<EngineType, HostProvider> providers;

    /**
     * Constructor sets the specific type of the executed engine in the context.
     * @param engineType type of grid engine
     */
    public HostOperationProviderService(@Value("${grid.engine.type}") final EngineType engineType) {
        this.engineType = engineType;
    }

    /**
     * This method processes the request to provider and returns listing of hosts.
     *
     * @return {@link Listing} of {@link Host}
     */
    public Listing<Host> hostListing() {
        return getProvider().listHosts();
    }

    /**
     * Injects all available {@link HostProvider} implementations.
     *
     * @param providers list of HostProvider.
     * @see HostProvider
     */
    @Autowired
    public void setProviders(final List<HostProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(HostProvider::getProviderType, Function.identity()));
    }

    private HostProvider getProvider() {
        final HostProvider hostProvider = providers.get(engineType);
        Assert.notNull(hostProvider, String.format("Provider for type '%s' isn`t supported", engineType));
        return hostProvider;
    }
}
