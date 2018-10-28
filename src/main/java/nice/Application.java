package nice;

import nice.constants.Status;
import nice.daos.TaskDao;
import nice.daos.TodoListDao;
import nice.daos.UserDao;
import nice.entities.Task;
import nice.entities.TodoList;
import nice.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

/**
 * Application for RESTful web services user, task and todolist.
 */
@SpringBootApplication
public class Application {

    /**
     * Method of initial data for application.
     *
     * @param userDao     Dao for users.
     * @param taskDao     Dao for tasks.
     * @param todoListDao Dao for todolists.
     */
    @Bean
    CommandLineRunner init(UserDao userDao, TaskDao taskDao,
                           TodoListDao todoListDao) {

        return (evt) -> {
            // users.
            User user1 = userDao.save(new User("niceUser1"));
            User user2 = userDao.save(new User("niceUser2"));
            User user3 = userDao.save(new User("niceUser3"));

            // tasks
            Task task1 = taskDao.save(new Task("task1", "desc task1",
                    Status.NOT_STARTED, user1));
            Task task2 = taskDao.save(new Task("task2", "desc task2",
                    Status.COMPLETE, user2));
            Task task3 = taskDao.save(new Task("task3", "desc task3",
                    Status.IN_PROGRESS, user3));

            // todolists.
            TodoList todoList1 = todoListDao.save(new TodoList("todolist1", null));
            TodoList todoList2 = todoListDao.save(new TodoList("todolist2", null));

            List<Task> tasks1 = Arrays.asList(task1, task2);
            List<Task> tasks2 = Arrays.asList(task3);

            todoList1.setTasks(tasks1);
            todoListDao.save(todoList1);

            todoList2.setTasks(tasks2);
            todoListDao.save(todoList2);
        };

    }

    /**
     * Starts the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}