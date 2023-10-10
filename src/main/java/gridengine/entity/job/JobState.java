package gridengine.entity.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents job states.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobState {

    /**
     * Job`s category.
     */
    private Category category;
    /**
     * Job`s state.
     */
    private String state;
    /**
     * Job`s state code.
     */
    private String stateCode;

    /**
     * Possible job`s categories.
     * To get more information about grid engine possible category values see <a href="https://manpages.debian.org/testing/gridengine-common/sge_status.5.en.html">
     * Grid Engine category values </a>
     */
    public enum Category {
        PENDING,
        RUNNING,
        SUSPENDED,
        ERROR,
        DELETED,
        FINISHED,
        UNKNOWN
    }
}
