package nice.services;

import nice.daos.UserDao;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.UserNameAlreadyTakenException;
import nice.exceptions.UserNotFoundException;
import nice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for user.
 *
 * @author danielctrenado@gmail.com
 */
@Service
@Transactional
public class UserService {

    //***************************************************************
    // Instance variables
    //***************************************************************

    @Autowired
    private UserDao userDao;

    //***************************************************************
    // Public methods.
    //***************************************************************

    /**
     * Obtains all the users.
     *
     * @return The list with all the users.
     */
    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    /**
     * Creates a user.
     *
     * @param userName The userName.
     * @return The new user.
     * @throws NotValidParameterException when the parameter is null or empty.
     * @throws UserNameAlreadyTakenException When userName already exists.
     */
    public User createUser(String userName)
            throws NotValidParameterException, UserNameAlreadyTakenException {
        if (userName == null || userName.trim().isEmpty()) {
            throw new NotValidParameterException("Error when creating user: userName is null or empty.");
        }
        Optional<User> optionalUser = this.userDao.findByUserName(userName);
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyTakenException("Error when creating user: userName " + userName + " already taken.");
        }
        User user = new User(userName);
        return userDao.save(user);
    }

    /**
     * Updates a user.
     *
     * @param id       The id of the user.
     * @param userName The userName of the user.
     * @return The new user.
     * @throws NotValidParameterException When the parameter is null or empty.
     * @throws UserNotFoundException when user was not found.
     */
    public User updateUser(Long id, String userName)
            throws NotValidParameterException, UserNotFoundException, UserNameAlreadyTakenException {

        if (id == null) throw new NotValidParameterException("Error when updating user: id is null.");
        if (userName == null) throw new NotValidParameterException("userName is null.");

        Optional<User> optionalUser = userDao.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("Error when updating user: The user with id " + id + " was not found.");
        }

        Optional<User> optionalUserName = this.userDao.findByUserName(userName);
        if (optionalUserName.isPresent() && optionalUserName.get().getId() != id) {
            throw new UserNameAlreadyTakenException("Error when updating user: userName " + userName + " already taken.");
        }

        User user = optionalUser.get();
        user.setUserName(userName);
        return userDao.save(user);
    }

    /**
     * Deletes a user.
     *
     * @param id The id of the user to delete.
     * @throws NotValidParameterException When the parameter is null.
     * @throws UserNotFoundException When user was not found.
     */
    public void deleteUser(Long id)
            throws NotValidParameterException, UserNotFoundException {
        if (id == null) {
            throw new NotValidParameterException("Error when deleting user: id is null.");
        }
        Optional<User> user = this.userDao.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Error when updating user: The user with id " + id + "was not found");
        }
        this.userDao.delete(user.get());
    }

}