package nice.services;

import nice.constants.Status;
import nice.daos.TaskDao;
import nice.daos.TodoListDao;
import nice.dto.RequestTodoListDto;
import nice.entities.Task;
import nice.entities.TodoList;
import nice.exceptions.NotValidParameterException;
import nice.exceptions.TodoListNameAlreadyTakenException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Tests for TodoListService.
 *
 * @author danielctrenado@gmail.com
 */
public class TodoListServiceTest {

    @Mock
    private TodoListDao todoListDao;

    @Mock
    private TaskDao taskDao;

    @InjectMocks
    private TodoListService todoListService = new TodoListService();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Successful Scenario for create todolist.
     *
     * @throws NotValidParameterException        If we have an invalid parameter.
     * @throws TodoListNameAlreadyTakenException If the name of the todolist already exists.
     */
    @Test
    public void createTodoListTest_successfulScenario()
            throws NotValidParameterException, TodoListNameAlreadyTakenException {
        //given
        String todoListName = "todoListName";
        List<Long> tasks = Arrays.asList(1L);
        RequestTodoListDto requestTodoListDto = new RequestTodoListDto(todoListName, tasks);
        Task task1 = new Task(1, todoListName, "taskDesc", Status.NOT_STARTED, null);
        TodoList expectedTodoList = new TodoList(1, todoListName, Arrays.asList(task1));

        Mockito.when(todoListDao.findByName(todoListName)).thenReturn(Optional.empty());
        Mockito.when(taskDao.findById(1L)).thenReturn(Optional.of(task1));
        Mockito.when(todoListDao.save(Mockito.any(TodoList.class))).thenReturn(expectedTodoList);

        //when
        TodoList actualTodoList = todoListService.createTodoList(requestTodoListDto);

        //then
        Mockito.verify(todoListDao, Mockito.times(1)).save(Mockito.any(TodoList.class));
        assertEquals(expectedTodoList, actualTodoList);
    }

    /**
     * Fail scenario for create todolist when name of the todolist is null.
     *
     * @throws NotValidParameterException        If we have an invalid parameter.
     * @throws TodoListNameAlreadyTakenException If the name of the todolist already exists.
     */
    @Test(expected = NotValidParameterException.class)
    public void createTodoListTest_failScenario_nameNull()
            throws NotValidParameterException, TodoListNameAlreadyTakenException {
        //given
        String todoListName = null;
        RequestTodoListDto requestTodoListDto = new RequestTodoListDto(todoListName, null);

        //when.
        todoListService.createTodoList(requestTodoListDto);
    }

    /**
     * Fail scenario for create todolist when name of the todolist is empty.
     *
     * @throws NotValidParameterException        If we have an invalid parameter.
     * @throws TodoListNameAlreadyTakenException If the name of the todolist already exists.
     */
    @Test(expected = NotValidParameterException.class)
    public void createTodoListTest_failScenario_nameEmpty()
            throws NotValidParameterException, TodoListNameAlreadyTakenException {
        //given
        String todoListName = "";
        RequestTodoListDto requestTodoListDto = new RequestTodoListDto(todoListName, null);

        //when.
        todoListService.createTodoList(requestTodoListDto);
    }

    /**
     * Fail scenario for create todoList when name of the todolist already exits.
     *
     * @throws NotValidParameterException        If we have an invalid parameter.
     * @throws TodoListNameAlreadyTakenException If the name of the todolist already exists.
     */
    @Test(expected = TodoListNameAlreadyTakenException.class)
    public void createTodoListTest_failScenario_nameAlreadyExists()
            throws NotValidParameterException, TodoListNameAlreadyTakenException {
        //given
        String todoListName = "todoListName";
        List<Long> tasks = Arrays.asList(1L);
        RequestTodoListDto requestTodoListDto = new RequestTodoListDto(todoListName, tasks);
        Task task1 = new Task(1, todoListName, "taskDesc", Status.NOT_STARTED, null);
        TodoList expectedTodoList = new TodoList(1, todoListName, Arrays.asList(task1));
        TodoList existingTodoList = new TodoList(2, todoListName, null);

        Mockito.when(todoListDao.findByName(todoListName)).thenReturn(Optional.of(existingTodoList));
        Mockito.when(taskDao.findById(1L)).thenReturn(Optional.of(task1));

        //when
        todoListService.createTodoList(requestTodoListDto);
    }


}