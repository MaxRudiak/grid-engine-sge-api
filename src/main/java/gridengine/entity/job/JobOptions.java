package gridengine.entity.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * This class contains job submission options.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobOptions {

    /**
     * Path to the script file or executable command to be invoked.
     */
    private String command;
    /**
     * Custom job name.
     */
    private String name;
    /**
     * Flag of the executable file.
     */
    private boolean canBeBinary;
    /**
     * Indication of usage of all environment variables.
     */
    private boolean useAllEnvVars;
    /**
     * The path to the storage of with result of job execution.
     */
    private String workingDir;
    /**
     * Job priority.
     */
    private int priority;
    /**
     * A list of queues where a job can be proceeded.
     */
    private List<String> queues;
    /**
     * Collection of used environment variables.
     */
    private Map<String, String> envVariables;
    /**
     * Settings of the parallel processing environment.
     */
    private ParallelEnv parallelEnv;
    /**
     * List of arguments for command to be processed.
     */
    private List<String> arguments;
    private String stdErrPath;
    private String stdOutPath;
}
