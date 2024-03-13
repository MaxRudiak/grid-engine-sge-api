package gridengine.provider.utils;

import gridengine.entity.job.JobOptions;
import gridengine.entity.job.ParallelEnv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sun.util.resources.ext.CalendarData_da;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QsubCommandParser {

    private static final String FIND_ID_PATTERN = "\\s\\d+\\s";
    private static final String COMMAND_BODY = "qsub";
    private static final String BINARY = "-b";
    private static final String IS_BINARY = "y";
    private static final String USE_ALL_VARS = "-V";
    private static final String COMMAND_NAME = "-N";
    private static final String WORK_DIR = "-wd";
    private static final String ERR_PATH = "-e";
    private static final String OUT_PATH = "-o";
    private static final String PRIORITY = "-p";
    private static final String QUEUES = "-q";
    private static final String SET_VARIABLE = "-v";
    private static final String PARALLEL_ENV = "-pe";
    private static final String EMPTY_STRING = "";
    private static final String COMMA = ",";

    public static Optional<String> parseJobId(final String jobString) {
        Pattern pattern = Pattern.compile(FIND_ID_PATTERN);
        Matcher matcher = pattern.matcher(jobString);
        return matcher.find()
                ? Optional.of(matcher.group()).map(String::trim)
                : Optional.empty();
    }

    public static String[] makeQsubCommand(final JobOptions options) {
        if (!isValidJob(options)) {
            throw new IllegalStateException("Exit code: 1; Error output: Invalid jobOptions structure");
        }
        final List<String> commands = new ArrayList<>();
        commands.add(COMMAND_BODY);
        if (options.isCanBeBinary()) {
            commands.add(BINARY);
            commands.add(IS_BINARY);
        }
        if (options.isUseAllEnvVars()) {
            commands.add(USE_ALL_VARS);
        }
        if (options.getName() != null && !options.getName().isEmpty()) {
            commands.add(COMMAND_NAME);
            commands.add(options.getName());
        }
        if (options.getWorkingDir() != null && !options.getWorkingDir().isEmpty()) {
            commands.add(WORK_DIR);
            commands.add(options.getWorkingDir());
        }
        if (options.getStdErrPath() != null && !options.getStdErrPath().isEmpty()) {
            commands.add(ERR_PATH);
            commands.add(options.getStdErrPath());
        }
        if (options.getStdOutPath() != null && !options.getStdOutPath().isEmpty()) {
            commands.add(OUT_PATH);
            commands.add(options.getStdOutPath());
        }
        if (options.getPriority() != 0) {
            commands.add(PRIORITY);
            commands.add(Integer.toString(options.getPriority()));
        }
        if (options.getQueues() != null && options.getQueues().isEmpty()) {
            commands.add(QUEUES);
            final StringBuilder queues = new StringBuilder();
            options.getQueues().forEach(q -> queues.append(q).append(COMMA));
            commands.add(queues.toString());
        }
        if (options.getEnvVariables() != null && !options.getEnvVariables().isEmpty()) {
            commands.add(SET_VARIABLE);
            final Map<String, String> envVarMap = options.getEnvVariables();
            commands.add(getVariablesStringFromMap(envVarMap));
        }
        final ParallelEnv parallelEnv = options.getParallelEnv();
        if (parallelEnv != null) {
            commands.add(PARALLEL_ENV);
            commands.add(parallelEnv.getName() == null
                    ? EMPTY_STRING
                    : parallelEnv.getName());
            final String params = parallelEnv.getMin()
                    + (parallelEnv.getMax() != 0
                    ? "-" + parallelEnv.getMax()
                    : EMPTY_STRING);
            commands.add(params);
        }
        commands.add(options.getCommand().trim());
        if (options.getArguments() != null && !options.getArguments().isEmpty()) {
            commands.addAll(options.getArguments());
        }
        return commands.toArray(new String[0]);
    }

    private static boolean isValidJob(final JobOptions options) {
        return options.getCommand() != null && !options.getCommand().isEmpty();
    }

    private static String getVariablesStringFromMap(final Map<String, String> varMap) {
        return varMap.entrySet().stream()
                .map(e -> e.getKey() + (!e.getValue().isEmpty()
                        ? "=" + e.getValue()
                        : EMPTY_STRING))
                .collect(Collectors.joining(COMMA));
    }
}
