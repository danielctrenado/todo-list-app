package nice.daos;

import nice.constants.Status;
import nice.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for task.
 *
 * @author danielctrenado@gmail.com
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Long> {
    Iterable<Task> findByStatus(Status status);
    Optional<Task> findByName(String name);
}
