package nice.daos;

import nice.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for user.
 *
 * @author danielctrenado@gmail.com
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}