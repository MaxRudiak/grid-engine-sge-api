package gridengine.cmd;

import gridengine.entity.CommandResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SimpleCmdExecutorTest {

    private static final List<String> EMPTY_LIST  = Collections.emptyList();
    private static final String[] WINDOWS_SUCCESSFUL_COMMAND = {"cmd.exe", "/c", "echo \"hello\""};
    private static final String[] LINUX_SUCCESSFUL_COMMAND = {"sh", "-c", "echo \"hello\""};
    private static final String[] MACOS_SUCCESSFUL_COMMAND = {"/bin/bash", "-c", "echo \"hello\""};
    private static final String[] WINDOWS_NOT_EXISTING_FUNCTION = {"cmd.exe", "/c", "eco \"hello\""};
    private static final String[] LINUX_NOT_EXISTING_FUNCTION = {"sh", "-c", "eco \"hello\""};
    private static final String[] MACOS_NOT_EXISTING_FUNCTION = {"/bin/bash", "-c", "eco \"hello\""};
    private static final String[] WINDOWS_INVALID_COMMAND = {"cfghmd.exe", "/c", "echo \"hello\""};
    private static final String[] LINUX_INVALID_COMMAND = {"serh", "-c", "echo \"hello\""};
    private static final String[] MACOS_INVALID_COMMAND = {"/bin/bash", "-c", "echo \"hello\""};
    private final SimpleCmdExecutor executeImpl = new SimpleCmdExecutor();

    @Test
    public void shouldSucceed() {
        CommandResult returnObject = getSuccessResultForSuitableOS(getOperationSystem(), executeImpl);
        Assertions.assertEquals(0, returnObject.getExitCode());
        Assertions.assertEquals(EMPTY_LIST, returnObject.getStdErr());
        Assertions.assertNotEquals(EMPTY_LIST, returnObject.getStdOut());
    }

    private static String getOperationSystem() {
        return System.getProperty("os.name")
                .toLowerCase(Locale.US);
    }

    private static CommandResult getSuccessResultForSuitableOS(String operationSystem, SimpleCmdExecutor executor) {
        if (operationSystem.equals("windows")) {
            return executor.execute(WINDOWS_SUCCESSFUL_COMMAND);
        } else if (operationSystem.equals("mac")) {
            return executor.execute(MACOS_SUCCESSFUL_COMMAND);
        } else {
            return executor.execute(LINUX_SUCCESSFUL_COMMAND);
        }
    }
}
