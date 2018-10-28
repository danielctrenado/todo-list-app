package nice.controllers;

import nice.dto.RequestTodoListDto;
import nice.dto.ResponseTaskDto;
import nice.dto.ResponseTodoListDto;
import nice.entities.Task;
import nice.entities.TodoList;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.TodoListNameAlreadyTakenException;
import nice.exceptions.TodoListNotFoundException;
import nice.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * RESTful webservice for todolist.
 *
 * @author danielctrenado@gmail.com
 */
@RestController
@RequestMapping("/api/v1")
public class TodoListController {

    //***************************************************************
    // Instance variables
    //***************************************************************

    /**
     * The service to perform todolists operations.
     */
    @Autowired
    private TodoListService todoListService;

    //***************************************************************
    // Public methods.
    //***************************************************************

    @GetMapping(path = "/todolists", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<ResponseTodoListDto>> getAllTodoLists() {
        Iterable<TodoList> todoLists = this.todoListService.getAllTodoLists();
        List<ResponseTodoListDto> responseTodoListDtos = StreamSupport.stream(todoLists.spliterator(), false)
                .map(todoList -> new ResponseTodoListDto(todoList))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseTodoListDtos, HttpStatus.OK);
    }

    /**
     * Obtains a todolist by id.
     *
     * @param id The id of the todolist to obtain.
     * @return The todolist associated to the id.
     * @throws TodoListNotFoundException  If the todolist was not found.
     * @throws NotValidParameterException If the patameter is not valid.
     */
    @GetMapping(path = "/todolists/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseTodoListDto> getTodoListById(@PathVariable("id") Long id)
            throws TodoListNotFoundException, NotValidParameterException {
        TodoList todoList = this.todoListService.getTodoListById(id);
        ResponseTodoListDto responseTodoListDto = new ResponseTodoListDto(todoList);
        return new ResponseEntity<>(responseTodoListDto, HttpStatus.OK);
    }

    /**
     * Creates a todolist.
     *
     * @param requestTodoListDto The new todolist.
     * @return The todolist created.
     * @throws NotValidParameterException        If the parameter is not valid.
     * @throws TodoListNameAlreadyTakenException If the name of the todolist already exists.
     */
    @PostMapping(path = "/todolists", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseTodoListDto> createTodoList(@RequestBody RequestTodoListDto requestTodoListDto)
            throws NotValidParameterException, TodoListNameAlreadyTakenException {
        TodoList todoList = this.todoListService.createTodoList(requestTodoListDto);
        ResponseTodoListDto responseTodoListDto = new ResponseTodoListDto(todoList);
        return new ResponseEntity<>(responseTodoListDto, HttpStatus.OK);
    }

    /**
     * Obtains the Tasks of specific todolist.
     *
     * @param id The id of todolist.
     * @return The list of task associated to todolist id.
     * @throws TodoListNotFoundException  If the todolist was not found.
     * @throws NotValidParameterException If the patameter is not valid.
     */
    @GetMapping(path = "/todolists/{id}/tasks", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ResponseTaskDto>> getTodoListTasksById(@PathVariable("id") Long id)
            throws TodoListNotFoundException, NotValidParameterException {
        Iterable<Task> tasks = this.todoListService.getTodoListTasks(id);
        List<ResponseTaskDto> responseTaskDtoList = StreamSupport.stream(tasks.spliterator(), false)
                .map(task -> new ResponseTaskDto(task))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseTaskDtoList, HttpStatus.OK);
    }

    /**
     * Deletes a todolist.
     *
     * @param id The id of the todolist to delete.
     * @return The result of the operation.
     * @throws TodoListNotFoundException  If the todolist was not found.
     * @throws NotValidParameterException If the parameter is not valid,
     */
    @DeleteMapping(path = "/todolists/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteTodoList(@PathVariable("id") Long id)
            throws TodoListNotFoundException, NotValidParameterException {
        this.todoListService.deleteTodoList(id);
        return new ResponseEntity<>("{\"result\":\"todolist was deleted successfully.\"}", HttpStatus.OK);
    }

}
