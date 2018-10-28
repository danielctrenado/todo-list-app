package nice.controllers;

import nice.constants.Status;
import nice.dto.RequestTaskDto;
import nice.dto.ResponseTaskDto;
import nice.entities.Task;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.TaskNameAlreadyTakenException;
import nice.exceptions.TaskNotFoundException;
import nice.exceptions.UserNotFoundException;
import nice.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * RESTful webservice for task.
 *
 * @author danielctrenado@gmail.com
 */
@RestController
@RequestMapping("/api/v1")
public class TaskController {

    //***************************************************************
    // Static variables
    //***************************************************************

    /**
     * Logger
     */
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    //***************************************************************
    // Instance variables
    //***************************************************************

    /**
     * The service to perform tasks operations.
     */
    @Autowired
    private TaskService taskService;

    //***************************************************************
    // Public methods.
    //***************************************************************

    /**
     * Obtains all tasks or the list of tasks with status according to the parameter status.
     *
     * @param status The status of the tasks.
     * @return all the tasks or if the parameter status is provided the list of the task with that status.
     */
    @GetMapping(path = "/tasks", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Iterable<ResponseTaskDto> getAllTasks(@RequestParam(value = "status", required = false) Status status) {
        Iterable<Task> tasks;
        logger.info("--> getAllTasks with status " + status);
        if (status == null) {
            tasks = this.taskService.getAllTasks();
        } else {
            tasks = this.taskService.getAllTasksByStatus(status);
        }

        List<ResponseTaskDto> responseTaskDtoList = StreamSupport.stream(tasks.spliterator(), false)
                .map(task -> new ResponseTaskDto(task))
                .collect(Collectors.toList());

        return responseTaskDtoList;
    }

    /**
     * Creates a task
     *
     * @param requestTaskDto The object with the data for the new task.
     * @return The new task.
     * @throws NotValidParameterException    If the parameter is not valid.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign was not found.
     */
    @PostMapping(path = "/tasks", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseTaskDto> createTask(@RequestBody RequestTaskDto requestTaskDto)
            throws NotValidParameterException, TaskNameAlreadyTakenException, UserNotFoundException {
        Task task = this.taskService.createTask(requestTaskDto);
        ResponseTaskDto responseTaskDto = new ResponseTaskDto(task);
        return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
    }

    /**
     * Updates a task.
     *
     * @param id             The id of the task to update.
     * @param requestTaskDto The object with the data of the task to be updated.
     * @return The task updated.
     * @throws NotValidParameterException    If a parameter is not valid.
     * @throws TaskNotFoundException         If the task was not found.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the userName to assign of is not found.
     */
    @PutMapping(path = "/tasks/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseTaskDto> updateTask(@PathVariable("id") Long id,
                                                      @RequestBody RequestTaskDto requestTaskDto)
            throws NotValidParameterException, TaskNotFoundException,
            TaskNameAlreadyTakenException, UserNotFoundException {
        Task task = this.taskService.updateTask(id, requestTaskDto);
        ResponseTaskDto responseTaskDto = new ResponseTaskDto(task);
        return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
    }

}
