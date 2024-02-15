package gridengine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommandResult {
    private List<String> stdOut;
    private List<String> stdErr;
    private int exitCode;
}
