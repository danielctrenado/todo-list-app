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
 * Tests for TaskService.
 *
 * @author danielctrenado@gmail.com
 */
public class TaskServiceTest {

    @Mock
    private TaskDao taskDao;

    @Mock
    private UserDao userDao;


    @InjectMocks
    private TaskService taskService = new TaskService();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Successful Scenario for create task.
     *
     * @throws NotValidParameterException    If we have an invalid parameter.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign was not found.
     */
    @Test
    public void createTaskTest_successfulScenario()
            throws TaskNameAlreadyTakenException, NotValidParameterException, UserNotFoundException {
        //given
        String taskName = "taskName";
        String descTask = "descTask";
        String userName = "batman";
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, userName);

        User assignedUser = new User(1, userName);
        Task expectedTask = new Task(taskName, descTask, status, assignedUser);
        expectedTask.setId(1);

        Mockito.when(taskDao.findByName(taskName)).thenReturn(Optional.empty());
        Mockito.when(userDao.findByUserName(userName)).thenReturn(Optional.of(new User(1, userName)));
        Mockito.when(taskDao.save(Mockito.any(Task.class))).thenReturn(expectedTask);

        //when
        Task actualTask = taskService.createTask(requestTaskDto);

        //then
        Mockito.verify(taskDao, Mockito.times(1)).save(Mockito.any(Task.class));
        assertEquals(expectedTask, actualTask);
    }

    /**
     * Fail scenario for create task when name is null.
     *
     * @throws NotValidParameterException    If we have an invalid parameter.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign was not found.
     */
    @Test(expected = NotValidParameterException.class)
    public void createTaskTest_failScenario_nameNull() throws TaskNameAlreadyTakenException,
            NotValidParameterException, UserNotFoundException {
        //given
        String taskName = null;
        String descTask = "descTask";
        String userName = "batman";
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, userName);

        //when.
        taskService.createTask(requestTaskDto);
    }

    /**
     * Fail scenario for create task when name is empty.
     *
     * @throws NotValidParameterException    If we have an invalid parameter.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign was not found.
     */
    @Test(expected = NotValidParameterException.class)
    public void createTaskTest_failScenario_nameEmpty() throws TaskNameAlreadyTakenException,
            NotValidParameterException, UserNotFoundException {
        //given
        String taskName = "";
        String descTask = "descTask";
        String userName = "batman";
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, userName);

        //when.
        taskService.createTask(requestTaskDto);
    }

    /**
     * Fail scenario for create task when name already exits.
     *
     * @throws NotValidParameterException    If we have an invalid parameter.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign was not found.
     */
    @Test(expected = TaskNameAlreadyTakenException.class)
    public void createTaskTest_failScenario_nameAlreadyExists()
            throws TaskNameAlreadyTakenException, NotValidParameterException, UserNotFoundException {
        //given
        String taskName = "taskName";
        String descTask = "descTask";
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, null);
        Task existingTaskWithSameName = new Task(2, taskName, null, null, null);

        Mockito.when(taskDao.findByName(taskName)).thenReturn(Optional.of(existingTaskWithSameName));

        //when
        taskService.createTask(requestTaskDto);
    }

    /**
     * Successful Scenario for update task.
     *
     * @throws NotValidParameterException    If the parameters are not valid.
     * @throws TaskNotFoundException         If the task is not found.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign is not found.
     */
    @Test
    public void updateTaskTest_successfulScenario()
            throws NotValidParameterException, TaskNameAlreadyTakenException,
            TaskNotFoundException, UserNotFoundException {
        //given
        long idTask = 1;
        String taskName = "taskName";
        String descTask = "descTask";
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, null);

        Task expectedTask = new Task(idTask, taskName, descTask, status, null);
        Optional<Task> optionalTask = Optional.of(expectedTask);

        Mockito.when(taskDao.findById(idTask)).thenReturn(optionalTask);
        Mockito.when(taskDao.findByName(taskName)).thenReturn(Optional.empty());
        Mockito.when(taskDao.save(Mockito.any(Task.class))).thenReturn(expectedTask);

        //when
        Task actualTask = taskService.updateTask(idTask, requestTaskDto);

        //then
        Mockito.verify(taskDao, Mockito.times(1)).findById(idTask);
        Mockito.verify(taskDao, Mockito.times(1)).findByName(taskName);
        Mockito.verify(taskDao, Mockito.times(1)).save(Mockito.any(Task.class));
        assertEquals(expectedTask, actualTask);
    }

    /**
     * Fail Scenario for update task when task id is not found.
     *
     * @throws NotValidParameterException    If the parameters are not valid.
     * @throws TaskNotFoundException         If the task is not found.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign is not found.
     */
    @Test(expected = TaskNotFoundException.class)
    public void updateUserTest_failScenario_taskNotFoud()
            throws NotValidParameterException, TaskNameAlreadyTakenException, TaskNotFoundException,
            UserNotFoundException {
        //given
        long id = -1;
        String taskName = "taskName";
        String descTask = "descTask";
        String userName = null;
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, userName);
        Optional<Task> optionalTask = Optional.empty();

        Mockito.when(taskDao.findById(id)).thenReturn(optionalTask);

        //when
        taskService.updateTask(id, requestTaskDto);
    }

    /**
     * Fail Scenario for update task when parameter id not valid.
     *
     * @throws NotValidParameterException    If the parameters are not valid.
     * @throws TaskNotFoundException         If the task is not found.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign is not found.
     */
    @Test(expected = NotValidParameterException.class)
    public void updateTaskTest_failScenario_parameterIdNotValid()
            throws NotValidParameterException, TaskNameAlreadyTakenException,
            TaskNotFoundException, UserNotFoundException {
        //given
        Long id = null;
        String taskName = "taskName";
        String descTask = "descTask";
        String userName = null;
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, userName);

        //when
        taskService.updateTask(id, requestTaskDto);
    }

    /**
     * Fail Scenario for update task when parameter name is not valid.
     *
     * @throws NotValidParameterException    If the parameters are not valid.
     * @throws TaskNotFoundException         If the task is not found.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign is not found.
     */
    @Test(expected = NotValidParameterException.class)
    public void updateTaskTest_failScenario_ParameterNameNotValid()
            throws NotValidParameterException, TaskNameAlreadyTakenException, TaskNotFoundException,
            UserNotFoundException {
        //given
        Long id = 1L;
        String taskName = null;
        String descTask = "descTask";
        String userName = null;
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, userName);

        //when
        taskService.updateTask(id, requestTaskDto);
    }

    /**
     * Fail scenario for update task when name of the task already exists.
     *
     * @throws NotValidParameterException    If the parameters are not valid.
     * @throws TaskNotFoundException         If the task is not found.
     * @throws TaskNameAlreadyTakenException If the name of the task already exists.
     * @throws UserNotFoundException         If the user to assign is not found.
     */
    @Test(expected = TaskNameAlreadyTakenException.class)
    public void updateTaskTest_failScenario_nameAlreadyExists()
            throws NotValidParameterException, UserNotFoundException, TaskNameAlreadyTakenException,
            TaskNotFoundException {
        //given
        Long taskId = 1L;
        String taskName = "taskName";
        String descTask = "descTask";
        String userName = null;
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, userName);

        Task task = new Task(taskId, taskName, descTask, status, null);
        Task existingTaskWithSameName = new Task(2, taskName, null, null, null);

        Mockito.when(taskDao.findById(taskId)).thenReturn(Optional.of(task));
        Mockito.when(taskDao.findByName(taskName)).thenReturn(Optional.of(existingTaskWithSameName));

        //when
        taskService.updateTask(taskId, requestTaskDto);
    }

    /**
     * Successful scenario for obtain all tasks.
     */
    @Test
    public void getAllTasksTest_successfulScenario() {
        //given
        Task task1 = new Task(1, "tasK1", "descTask1", Status.IN_PROGRESS, null);
        Task task2 = new Task(2, "tasK2", "descTask2", Status.COMPLETE, null);

        List<Task> expectedTasks = Arrays.asList(task1, task2);
        Mockito.when(taskDao.findAll()).thenReturn(expectedTasks);

        //when
        List<Task> actualTasks = (List<Task>) this.taskService.getAllTasks();

        //then
        assertEquals(expectedTasks, actualTasks);
    }


}