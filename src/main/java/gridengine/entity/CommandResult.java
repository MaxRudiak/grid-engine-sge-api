package gridengine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CommandResult {
    private List<String> stdOut;
    private List<String> stdErr;
    private int exitCode;
}
