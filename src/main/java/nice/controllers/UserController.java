package nice.controllers;

import nice.dto.RequestUserDto;
import nice.dto.ResponseUserDto;
import nice.entities.User;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.UserNameAlreadyTakenException;
import nice.exceptions.UserNotFoundException;
import nice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * RESTful web service for users.
 *
 * @author danielctrenado@gmail.com
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {


    //***************************************************************
    // Instance variables
    //***************************************************************

    /**
     * The service to perform users operations.
     */
    @Autowired
    private UserService userService;

    //***************************************************************
    // Public methods.
    //***************************************************************

    /**
     * Obtains all users.
     *
     * @return The list of all users.
     */
    @GetMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<ResponseUserDto>> getAllUsers() {
        Iterable<User> users = this.userService.findAll();

        List<ResponseUserDto> responseUserDtos = StreamSupport.stream(users.spliterator(), false)
                .map(user -> new ResponseUserDto(user.getId(), user.getUserName()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseUserDtos, HttpStatus.OK);
    }

    /**
     * Creates a user.
     *
     * @param requestUserDto The data of new user to create.
     * @return The user created.
     * @throws NotValidParameterException    If the parameter is not valid.
     * @throws UserNameAlreadyTakenException If the userName already exists.
     */
    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody RequestUserDto requestUserDto)
            throws NotValidParameterException, UserNameAlreadyTakenException {
        User user = this.userService.createUser(requestUserDto.getUserName());

        ResponseUserDto responseUserDto = new ResponseUserDto(user.getId(), user.getUserName());

        return new ResponseEntity<>(responseUserDto, HttpStatus.OK);
    }

    /**
     * Updates a user.
     *
     * @param id             The id of the user to be updated.
     * @param requestUserDto The data to be updated.
     * @return The user updated.
     * @throws NotValidParameterException    If the parameter is not valid.
     * @throws UserNameAlreadyTakenException If the userName already exists.
     * @throws UserNotFoundException         If the user is not found.
     */
    @PutMapping(path = "/users/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable("id") Long id,
                                                      @RequestBody RequestUserDto requestUserDto)
            throws UserNotFoundException, NotValidParameterException, UserNameAlreadyTakenException {
        User user = userService.updateUser(id, requestUserDto.getUserName());

        ResponseUserDto responseUserDto = new ResponseUserDto(user.getId(), user.getUserName());

        return new ResponseEntity<>(responseUserDto, HttpStatus.OK);
    }

    /**
     * Deletes a user.
     *
     * @param id The id of the user to delete.
     * @return The result of the operation.
     * @throws NotValidParameterException If the parameter is not valid.
     * @throws UserNotFoundException      If the user is not found.
     */
    @DeleteMapping(path = "/users/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id)
            throws NotValidParameterException, UserNotFoundException {
        this.userService.deleteUser(id);
        return new ResponseEntity<>("\"{\"result\":\"User was deleted successfully\"}", HttpStatus.OK);
    }
}