package gridengine.cmd;

import gridengine.entity.CommandResult;

public interface CmdExecutor {
    CommandResult execute(String... arguments);
}
