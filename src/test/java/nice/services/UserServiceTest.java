package nice.services;

import nice.daos.UserDao;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.UserNameAlreadyTakenException;
import nice.exceptions.UserNotFoundException;
import nice.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Tests for UserService.
 *
 * @author danielctrenado@gmail.com
 */
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService = new UserService();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Successful Scenario for create user.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNameAlreadyTakenException If the username already exists.
     */
    @Test
    public void createUserTest_successfulScenario()
            throws NotValidParameterException, UserNameAlreadyTakenException {
        //given
        String userName = "batman";
        User expectedUser = new User(1, userName);
        Mockito.when(userDao.findByUserName(userName)).thenReturn(Optional.empty());
        Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(expectedUser);

        //when
        User actualUser = userService.createUser(userName);

        //then
        Mockito.verify(userDao, Mockito.times(1)).save(Mockito.any(User.class));
        assertEquals(expectedUser, actualUser);
    }

    /**
     * Fail scenario for create user when userName is null.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNameAlreadyTakenException If the username already exists.
     */
    @Test(expected = NotValidParameterException.class)
    public void createUserTest_failScenario_userNameNull()
            throws NotValidParameterException, UserNameAlreadyTakenException {
        //given.
        String userName = null;

        //when.
        userService.createUser(userName);
    }

    /**
     * Fail scenario for create user when userName is empty.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNameAlreadyTakenException If the username already exists.
     */
    @Test(expected = UserNameAlreadyTakenException.class)
    public void createUserTest_failScenario_userNameAlreadyExists()
            throws NotValidParameterException, UserNameAlreadyTakenException {
        //given
        String userName = "batman";
        Mockito.when(userDao.findByUserName(userName)).thenReturn(Optional.of(new User(1, userName)));

        //when
        userService.createUser(userName);
    }

    /**
     * Fail scenario for create user when userName already exists.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNameAlreadyTakenException If the username already exists.
     */
    @Test(expected = UserNameAlreadyTakenException.class)
    public void createUserTest_failScenario_userNameAlreadyTaken()
            throws NotValidParameterException, UserNameAlreadyTakenException {
        //given
        String userName = "batman";
        Mockito.when(userDao.findByUserName(userName)).thenReturn(Optional.of(new User(1, userName)));

        //when
        userService.createUser(userName);
    }

    /**
     * Successful Scenario for update user.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     * @throws UserNameAlreadyTakenException If the userName already exists.
     */
    @Test
    public void updateUserTest_successfulScenario()
            throws NotValidParameterException, UserNotFoundException, UserNameAlreadyTakenException {
        //given
        String userName = "batman";
        Long id = 1L;
        User expectedUser = new User(id, userName);
        expectedUser.setId(id);
        Optional<User> optionalUser = Optional.of(expectedUser);

        Mockito.when(userDao.findById(id)).thenReturn(optionalUser);
        Mockito.when(userDao.findByUserName(userName)).thenReturn(Optional.empty());
        Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(expectedUser);

        //when
        User actualUser = userService.updateUser(id, userName);

        //then
        Mockito.verify(userDao, Mockito.times(1)).findById(id);
        Mockito.verify(userDao, Mockito.times(1)).findByUserName(userName);
        Mockito.verify(userDao, Mockito.times(1)).save(Mockito.any(User.class));
        assertEquals(expectedUser, actualUser);
    }

    /**
     * Fail Scenario for update user when user is not found.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     * @throws UserNameAlreadyTakenException If the userName already exists.
     */
    @Test(expected = UserNotFoundException.class)
    public void updateUserTest_failScenario_userNotFoud()
            throws NotValidParameterException, UserNotFoundException, UserNameAlreadyTakenException {
        //given
        Long id = -1L;
        String userName = "batman";
        Optional<User> optionalUser = Optional.empty();

        Mockito.when(userDao.findById(id)).thenReturn(optionalUser);

        //when
        userService.updateUser(id, userName);
    }

    /**
     * Fail Scenario for update user when parameter id not valid.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     * @throws UserNameAlreadyTakenException If the userName already exists.
     */
    @Test(expected = NotValidParameterException.class)
    public void updateUserTest_failScenario_parameterIdNotValid()
            throws NotValidParameterException, UserNotFoundException, UserNameAlreadyTakenException {
        //given
        Long id = null;
        String userName = "batman";

        //when
        userService.updateUser(id, userName);
    }

    /**
     * Fail Scenario for update user when parameter userName not valid.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     * @throws UserNameAlreadyTakenException If the userName already exists.
     */
    @Test(expected = NotValidParameterException.class)
    public void updateUserTest_failScenario_ParameterUserNameNotValid()
            throws NotValidParameterException, UserNotFoundException, UserNameAlreadyTakenException {
        //given
        Long id = 1L;
        String userName = null;

        //when
        userService.updateUser(id, userName);
    }

    /**
     * Fail scenario for update user when userName already exists.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     * @throws UserNameAlreadyTakenException If the userName already exists.
     */
    @Test(expected = UserNameAlreadyTakenException.class)
    public void updateUserTest_failScenario_userNameAlreadyExists()
            throws NotValidParameterException, UserNameAlreadyTakenException, UserNotFoundException {
        //given
        long id = 1, idUsed = 2;
        String userName = "batman";

        Mockito.when(userDao.findById(id)).thenReturn(Optional.of(new User(id, userName)));
        Mockito.when(userDao.findByUserName(userName)).thenReturn(Optional.of(new User(idUsed, userName)));

        //when
        userService.updateUser(id, userName);
    }

    /**
     * Successful scenario for delete user.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     */
    @Test
    public void deleteUserTest_successfulScenario() throws NotValidParameterException, UserNotFoundException {
        //given
        long id = 1;
        String userName = "batman";
        Mockito.when(userDao.findById(id)).thenReturn(Optional.of(new User(id, userName)));

        //when
        this.userService.deleteUser(id);

        //then
        Mockito.verify(userDao, Mockito.times(1)).findById(id);
        Mockito.verify(userDao, Mockito.times(1)).delete(Mockito.any(User.class));
    }

    /**
     * Fail scenario for delete user when we have a not valid parameter.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     */
    @Test(expected = NotValidParameterException.class)
    public void deleteUserTest_failScenario_idIsNull() throws NotValidParameterException, UserNotFoundException {
        //given
        Long id = null;

        //when
        this.userService.deleteUser(id);
    }

    /**
     * Fail scenario for delete user when the user is not found.
     *
     * @throws NotValidParameterException If the parameters are not valid.
     * @throws UserNotFoundException If the user is not found.
     */
    @Test(expected = UserNotFoundException.class)
    public void deleteUserTest_failScenario_userNotFound() throws NotValidParameterException, UserNotFoundException {
        //given
        long id = -1;
        Mockito.when(userDao.findById(id)).thenReturn(Optional.empty());

        //when
        this.userService.deleteUser(id);
    }


    /**
     * Successful scenario for obtain all users.
     */
    @Test
    public void getAllUsersTest_successfulScenario() {
        //given
        User user1 = new User(1, "batman");
        User user2 = new User(2, "superman");
        List<User> expectedUsers = Arrays.asList(user1, user2);
        Mockito.when(userDao.findAll()).thenReturn(expectedUsers);

        //when
        List<User> actualUsers = (List<User>) this.userService.findAll();

        //then
        assertEquals(expectedUsers, actualUsers);
    }


}