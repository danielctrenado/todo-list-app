package nice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import nice.constants.Status;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Entity for task.
 *
 * @author danielctrenado@gmail.com
 */
@Entity
@Table(name = "tasks")
public class Task {

    //***************************************************************
    // Instance variables
    //***************************************************************

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "assigned_user_id")
    @JsonManagedReference
    private User user;

    @ManyToMany(mappedBy = "tasks")
    private List<TodoList> todoLists;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public Task() {
    }

    public Task(String name, String description, Status status, User user) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public Task(long id, String name, String description, Status status, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }

    //***************************************************************
    // Overrides
    //***************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                Objects.equals(user, task.user) &&
                Objects.equals(todoLists, task.todoLists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, user, todoLists);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", user=" + user +
                ", todoLists=" + todoLists +
                '}';
    }
}
