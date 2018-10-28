package nice.daos;

import nice.entities.TodoList;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * DAO for todolist.
 *
 * @author danielctrenado@gmail.com
 */
public interface TodoListDao extends CrudRepository<TodoList, Long> {
    Optional<TodoList> findByName(String name);
}
