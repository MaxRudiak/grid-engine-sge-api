package gridengine.provider.utils;

import gridengine.entity.job.JobOptions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    }

    private static boolean isValidJob(final JobOptions options) {
        return options.getCommand() != null && !options.getCommand().isEmpty();
    }
}
