package nice.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data Transfer Object for user.
 */
public class RequestUserDto implements Serializable {

    //***************************************************************
    // Instance variables
    //***************************************************************

    private String userName;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public RequestUserDto() {
    }

    public RequestUserDto(String userName) {
        this.userName = userName;
    }

    //***************************************************************
    // Getters and Setters.
    //***************************************************************

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
        RequestUserDto that = (RequestUserDto) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "RequestUserDto{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
