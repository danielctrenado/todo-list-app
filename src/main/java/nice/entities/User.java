package nice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;


/**
 * Entity for user.
 *
 * @author danielctrenado@gmail.com
 */
@Entity
@Table(name = "users")
public class User {

    //***************************************************************
    // Instance varibles
    //***************************************************************

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Task task;

    //***************************************************************
    // Constructors.
    //***************************************************************

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(long id, String userName) {
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    //***************************************************************
    // Overrides
    //***************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!(userName != null ? !userName.equals(user.userName) : user.userName != null)) return false;
        return task.equals(user.task);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", task=" + task +
                '}';
    }
}