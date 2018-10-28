package nice.dto;

import nice.constants.Status;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data Transfer Object for todolist.
 *
 * @author danielctrenado@gmail.com
 */

public class RequestTaskDto implements Serializable {

    //***************************************************************
    // Instance variables
    //***************************************************************

    private String name;

    private String description;

    private Status status;

    private String userName;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public RequestTaskDto() {
    }

    public RequestTaskDto(String name) {
        this.name = name;
    }

    public RequestTaskDto(String name, String description, Status status, String userName) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //***************************************************************
    // Overrides
    //***************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestTaskDto that = (RequestTaskDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                status == that.status &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, userName);
    }

    @Override
    public String toString() {
        return "RequestTaskDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                '}';
    }
}
