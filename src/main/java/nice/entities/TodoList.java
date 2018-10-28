package nice.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Entity for todolist.
 *
 * @author danielctrenado@gmail.com
 */
@Entity
@Table(name = "todolists")
public class TodoList {

    //***************************************************************
    // Instance variables
    //***************************************************************

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "todolists_tasks", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "todolist_id", referencedColumnName = "id"))
    private List<Task> tasks;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public TodoList() {
    }

    public TodoList(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public TodoList(long id, String name, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    //***************************************************************
    // Getters and Setters.
    //***************************************************************

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    //***************************************************************
    // Overrides
    //***************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoList todoList = (TodoList) o;
        return id == todoList.id &&
                Objects.equals(name, todoList.name) &&
                Objects.equals(tasks, todoList.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tasks);
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
