package gridengine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class returns a list of given element.
 * @param <T> takes object for listing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing<T> {

    private List<T> list;
}
