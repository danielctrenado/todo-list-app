package nice.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for todolist.
 *
 * @author danielctrenado@gmail.com
 */
public class RequestTodoListDto implements Serializable {

    //***************************************************************
    // Instance variables
    //***************************************************************

    private String name;

    private List<Long> tasks;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public RequestTodoListDto() {
    }

    public RequestTodoListDto(String name, List<Long> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    //***************************************************************
    // Getters and Setters.
    //***************************************************************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getTasks() {
        return tasks;
    }

    public void setTasks(List<Long> tasks) {
        this.tasks = tasks;
    }

    //***************************************************************
    // Overrides
    //***************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestTodoListDto that = (RequestTodoListDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tasks);
    }

    @Override
    public String toString() {
        return "RequestTodoListDto{" +
                "name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
