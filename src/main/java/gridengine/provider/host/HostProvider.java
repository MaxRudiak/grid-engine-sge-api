package gridengine.provider.host;

import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.host.Host;

/**
 * This interface specifies methods for a host provider.
 */
public interface HostProvider {
    Listing<Host> listHosts();
    EngineType getProviderType();
}
