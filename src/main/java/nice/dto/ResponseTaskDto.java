package nice.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import nice.constants.Status;
import nice.entities.Task;
import nice.entities.TodoList;
import nice.entities.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Response DTO for task.
 *
 * @author danielctrenado@gmail.com
 */
public class ResponseTaskDto {

    //***************************************************************
    // Instance variables
    //***************************************************************

    private long id;

    private String name;

    private String description;

    private Status status;

    @JsonProperty("user")
    private ResponseUserDto responseUserDto;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public ResponseTaskDto() {
    }

    public ResponseTaskDto(long id, String name, String description, Status status, ResponseUserDto responseUserDto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.responseUserDto = responseUserDto;
    }

    public ResponseTaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
        if (task.getUser() != null) {
            this.responseUserDto = new ResponseUserDto(task.getUser().getId(),
                    task.getUser().getUserName());
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

    public ResponseUserDto getResponseUserDto() {
        return responseUserDto;
    }

    public void setResponseUserDto(ResponseUserDto responseUserDto) {
        this.responseUserDto = responseUserDto;
    }

    //***************************************************************
    // Overrides
    //***************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseTaskDto that = (ResponseTaskDto) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                status == that.status &&
                Objects.equals(responseUserDto, that.responseUserDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, responseUserDto);
    }

    @Override
    public String toString() {
        return "ResponseTaskDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", responseUserDto=" + responseUserDto +
                '}';
    }
}
