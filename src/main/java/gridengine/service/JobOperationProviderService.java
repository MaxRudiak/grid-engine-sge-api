package gridengine.service;

import gridengine.entity.EngineType;
import gridengine.entity.Listing;
import gridengine.entity.job.Job;
import gridengine.provider.job.JobProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class defines operations for processing information from the user
 * and returns the results to the controller {@link gridengine.controller.job.JobOperationController}.
 */
@Service
public class JobOperationProviderService {

    private final EngineType engineType;
    /**
     * Collection of providers by engine type.
     */
    private Map<EngineType, JobProvider> providers;


    /**
     * Constructor sets the specific type of the executed engine.
     * @param engineType an engine for working with jobs.
     */
    public JobOperationProviderService(@Value("${grid.engine.type}") final EngineType engineType) {
        this.engineType = engineType;
    }

    public Listing<Job> jobListing() {
        return getJobProvider().listJobs();
    }

    /**
     * Creates a map of suppliers by engine type.
     * @param providers List of providers.
     */
    @Autowired
    public void setProviders(final List<JobProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(JobProvider::getProviderType, Function.identity()));
    }

    private JobProvider getJobProvider() {
        final JobProvider jobProvider = providers.get(engineType);
        Assert.notNull(jobProvider, String.format("Provider for type '%s' is not supported!", engineType));
        return  jobProvider;
    }
}
