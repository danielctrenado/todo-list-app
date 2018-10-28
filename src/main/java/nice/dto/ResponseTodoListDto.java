package nice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import nice.entities.TodoList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Response DTO for todolist.
 *
 * @author danielctrenado@gmail.com
 */
public class ResponseTodoListDto {

    //***************************************************************
    // Instance variables
    //***************************************************************

    private long id;

    private String name;

    @JsonProperty("tasks")
    private List<ResponseTaskDto> responseTaskDtos;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public ResponseTodoListDto() {
    }

    public ResponseTodoListDto(long id, String name, List<ResponseTaskDto> responseTaskDtos) {
        this.id = id;
        this.name = name;
        this.responseTaskDtos = responseTaskDtos;
    }

    public ResponseTodoListDto(TodoList todoList) {
        this.id = todoList.getId();
        this.name = todoList.getName();
        if (todoList.getTasks() != null) {
            this.responseTaskDtos = todoList.getTasks()
                    .stream()
                    .map(task -> new ResponseTaskDto(task))
                    .collect(Collectors.toList());
        }
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

    public List<ResponseTaskDto> getResponseTaskDtos() {
        return responseTaskDtos;
    }

    public void setResponseTaskDtos(List<ResponseTaskDto> responseTaskDtos) {
        this.responseTaskDtos = responseTaskDtos;
    }

    //***************************************************************
    // Overrides
    //***************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseTodoListDto that = (ResponseTodoListDto) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(responseTaskDtos, that.responseTaskDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, responseTaskDtos);
    }

    @Override
    public String toString() {
        return "ResponseTodoListDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", responseTaskDtos=" + responseTaskDtos +
                '}';
    }
}
