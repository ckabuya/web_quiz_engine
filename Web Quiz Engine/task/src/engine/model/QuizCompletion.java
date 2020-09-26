package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class QuizCompletion {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long pk;

    private long id;//quiz ID

    @JsonIgnore
    private String user;//Who took it
    @NotNull
    private Date completedAt;  // Date and time

    public QuizCompletion(long id, String user, Date dateCompleted) {
        this.id = id;
        this.user = user;
        this.completedAt = dateCompleted;
    }
    public QuizCompletion(long id, long pk,String user, Date dateCompleted) {
        this.id = id;
        this.pk  =pk;
        this.user = user;
        this.completedAt = dateCompleted;
    }

    public QuizCompletion() {
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizCompletion)) return false;
        QuizCompletion that = (QuizCompletion) o;
        return getPk() == that.getPk() &&
                getId() == that.getId() &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getCompletedAt(), that.getCompletedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPk(), getId(), getUser(), getCompletedAt());
    }
}
