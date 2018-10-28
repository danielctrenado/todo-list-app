package nice.services;

import nice.daos.TaskDao;
import nice.daos.TodoListDao;
import nice.dto.RequestTodoListDto;
import nice.entities.Task;
import nice.entities.TodoList;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.TodoListNameAlreadyTakenException;
import nice.exceptions.TodoListNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for todolist.
 *
 * @author danielctrenado@gmail.com
 */
@Service
public class TodoListService {

    //***************************************************************
    // Instance variables
    //***************************************************************

    @Autowired
    private TodoListDao todoListDao;

    @Autowired
    private TaskDao taskDao;

    //***************************************************************
    // Public methods.
    //***************************************************************

    /**
     * Obtains all the todolists.
     *
     * @return The list with all the todolists.
     */
    public Iterable<TodoList> getAllTodoLists() {
        return this.todoListDao.findAll();
    }

    /**
     * Obtains a todolist according to the parameter id.
     *
     * @param id The id of the todolist to get.
     * @return The todolist associated to the id.
     * @throws NotValidParameterException If the parameter is not valid.
     * @throws TodoListNotFoundException  If the todolist is not found.
     */
    public TodoList getTodoListById(Long id) throws NotValidParameterException, TodoListNotFoundException {
        if (id == null) {
            throw new NotValidParameterException("Error when obtaining a todolist: id is null.");
        }
        Optional<TodoList> todoList = this.todoListDao.findById(id);
        if (!todoList.isPresent()) {
            throw new TodoListNotFoundException("Error when obtaining a todolist: todolist with id "
                    + id + "was not found");
        }
        return todoList.get();
    }

    /**
     * Obtains a task associated to an specific todolist using parameter id.
     *
     * @param id The id of the todolist to get the tasks.
     * @return The list of Task associated to the todolist.
     * @throws NotValidParameterException If the parameter is not valid.
     * @throws TodoListNotFoundException  If the todolist is not found.
     */
    public Iterable<Task> getTodoListTasks(Long id) throws TodoListNotFoundException,
            NotValidParameterException {
        if (id == null) {
            throw new NotValidParameterException("Error when obtaining all tasks associated to todolist: " +
                    "id of todolist is null.");
        }
        Optional<TodoList> todoList = this.todoListDao.findById(id);
        if (!todoList.isPresent()) {
            throw new TodoListNotFoundException("Error when obtaining all tasks associated to todolist: " +
                    "todolist with id " + id + "was not found");
        }
        return todoList.get().getTasks();
    }

    /**
     * Creates a todolist.
     *
     * @param requestTodoListDto The todolist to create.
     * @return The new todolist.
     * @throws NotValidParameterException        If the parameters are not valid.
     * @throws TodoListNameAlreadyTakenException If the name of the todolist already exists.
     */
    public TodoList createTodoList(RequestTodoListDto requestTodoListDto)
            throws NotValidParameterException, TodoListNameAlreadyTakenException {
        List<Task> tasks = null;
        if (requestTodoListDto == null) {
            throw new NotValidParameterException("Error when creating todolist: todolist object is null.");
        }
        if (requestTodoListDto.getName() == null || requestTodoListDto.getName().trim().isEmpty()) {
            throw new NotValidParameterException("Error when creating todolist: the name of the todolist " +
                    "is null or empty.");
        }
        Optional<TodoList> optionalTodoList = this.todoListDao.findByName(requestTodoListDto.getName());
        if (optionalTodoList.isPresent()) {
            throw new TodoListNameAlreadyTakenException("Error when creating a todolist: the name of todolist "
                    + requestTodoListDto.getName() + " already exists.");
        }

        if (requestTodoListDto.getTasks() != null) {
            tasks = requestTodoListDto.getTasks()
                    .stream()
                    .map(idTask -> this.taskDao.findById(idTask))
                    .map(optionalTask -> optionalTask.isPresent() ? optionalTask.get() : null)
                    .filter(task -> task != null)
                    .collect(Collectors.toList());
        }
        TodoList todoList = new TodoList(requestTodoListDto.getName(), tasks);
        return this.todoListDao.save(todoList);
    }

    /**
     * Deletes a todolist.
     *
     * @param id The id of the todolist to delete.
     * @throws NotValidParameterException If the parameter is not valid.
     * @throws TodoListNotFoundException  If the todolist is not found.
     */
    public void deleteTodoList(Long id)
            throws NotValidParameterException, TodoListNotFoundException {
        if (id == null) {
            throw new NotValidParameterException("Error when deleting todolist: id is null.");
        }
        Optional<TodoList> todoList = this.todoListDao.findById(id);
        if (!todoList.isPresent()) {
            throw new TodoListNotFoundException("Error when deleting todolist: todolist with id "
                    + id + "was not found");
        }
        this.todoListDao.deleteById(id);
    }

}
