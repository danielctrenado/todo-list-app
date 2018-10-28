package nice.services;

import nice.constants.Status;
import nice.daos.TaskDao;
import nice.daos.UserDao;
import nice.dto.RequestTaskDto;
import nice.entities.Task;
import nice.entities.User;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.TaskNameAlreadyTakenException;
import nice.exceptions.TaskNotFoundException;
import nice.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for task.
 *
 * @author danielctrenado@gmail.com
 */
@Service
@Transactional
public class TaskService {

    //***************************************************************
    // Instance variables
    //***************************************************************

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private UserDao userDao;

    //***************************************************************
    // Public methods.
    //***************************************************************

    /**
     * Finds all the tasks.
     *
     * @return The list of the tasks.
     */
    public Iterable<Task> getAllTasks() {
        return this.taskDao.findAll();
    }

    /**
     * Finds all the tasks by status.
     *
     * @param status The status of the task.
     * @return List of tasks according to status parameter.
     */
    public Iterable<Task> getAllTasksByStatus(Status status) {
        return this.taskDao.findByStatus(status);
    }

    /**
     * Creates a task.
     *
     * @param requestTaskDto The task to create.
     * @return The task created.
     * @throws NotValidParameterException    If a parameter is not valid.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign is not found.
     */
    public Task createTask(RequestTaskDto requestTaskDto)
            throws NotValidParameterException, TaskNameAlreadyTakenException, UserNotFoundException {
        User user = null;
        // Validations.
        if (requestTaskDto == null) throw new NotValidParameterException("Error when creating task: task is null");
        if (requestTaskDto.getName() == null || requestTaskDto.getName().trim().isEmpty()) {
            throw new NotValidParameterException("Error when creating task: the name of the task is null or empty.");
        }
        Optional<Task> optionalTask = this.taskDao.findByName(requestTaskDto.getName());
        if (optionalTask.isPresent()) {
            throw new TaskNameAlreadyTakenException("Error when creating task: name " + requestTaskDto.getName() + " already taken.");
        }
        if (requestTaskDto.getUserName() != null) {
            Optional<User> optionalUser = this.userDao.findByUserName(requestTaskDto.getUserName());
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException("Error when creating task: userName " + requestTaskDto.getUserName() + " to assign the task was not found.");
            }
            user = optionalUser.get();
        }

        Task task = new Task(requestTaskDto.getName(), requestTaskDto.getDescription(),
                requestTaskDto.getStatus(), user);

        // Save task in db.
        return this.taskDao.save(task);
    }

    /**
     * Updates a task.
     *
     * @param id             The id of the task to update.
     * @param requestTaskDto The information of the task to update.
     * @return The task updated.
     * @throws NotValidParameterException    If a parameter is not valid.
     * @throws TaskNotFoundException         If the task to be updated was not found.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign is not found.
     */
    public Task updateTask(Long id, RequestTaskDto requestTaskDto)
            throws NotValidParameterException, TaskNotFoundException,
            TaskNameAlreadyTakenException, UserNotFoundException {
        User user = null;
        // Validations.
        if (id == null) throw new NotValidParameterException("Error when updating task: id is null.");
        if (requestTaskDto == null) throw new NotValidParameterException("Error when updating task: task is null");
        if (requestTaskDto.getName() == null || requestTaskDto.getName().trim().isEmpty()) {
            throw new NotValidParameterException("Error when updating task: the name of the task is null or empty.");
        }
        // find the user to assign the task if and only if userName is different from null and not empty.
        Optional<User> optionalUser = Optional.empty();
        if (requestTaskDto.getUserName() != null && !requestTaskDto.getUserName().trim().isEmpty()) {
            optionalUser = this.userDao.findByUserName(requestTaskDto.getUserName());
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException("Error when updating task: userName " + requestTaskDto.getUserName() + " to assign the task was not found.");
            }
            user = optionalUser.get();
        }
        // find the task to update.
        Optional<Task> optionalTask = this.taskDao.findById(id);
        if (!optionalTask.isPresent()) {
            throw new TaskNotFoundException("Error when updating task: The task with id " + id + " was not found.");
        }
        Optional<Task> optionalTaskName = this.taskDao.findByName(requestTaskDto.getName());
        if (optionalTaskName.isPresent() && optionalTaskName.get().getId() != id) {
            throw new TaskNameAlreadyTakenException("Error when updating task: name " + requestTaskDto.getName() + " already taken.");
        }

        Task task = optionalTask.get();

        // update the information.
        task.setName(requestTaskDto.getName());
        task.setDescription(requestTaskDto.getDescription());
        task.setStatus(requestTaskDto.getStatus());
        task.setUser(user);

        // Save the task updated.
        return this.taskDao.save(task);
    }

}
