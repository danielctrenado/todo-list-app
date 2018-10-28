package nice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import nice.entities.Task;

import javax.persistence.*;
import java.util.Objects;


/**
 * Response DTO for user.
 *
 * @author danielctrenado@gmail.com
 */
public class ResponseUserDto {

    //***************************************************************
    // Instance varibles
    //***************************************************************

    private long id;
    private String userName;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public ResponseUserDto() {
    }

    public ResponseUserDto(String userName) {
        this.userName = userName;
    }

    public ResponseUserDto(long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    //***************************************************************
    // Getters and Setters.
    //***************************************************************

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
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
        ResponseUserDto that = (ResponseUserDto) o;
        return id == that.id &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }

    @Override
    public String toString() {
        return "ResponseUserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}