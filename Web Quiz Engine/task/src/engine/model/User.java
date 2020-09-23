package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    // Password validation https://stackoverflow.com/questions/4459474/hibernate-validator-email-accepts-askstackoverflow-as-valid?noredirect=1&lq=1
    @Id
    @Email(regexp = ".+@.+\\..+",message = "Invalid email address")
    private String email;
    @NotBlank
    @Size(min=5, message = "Password must be atleast five characters")
    private String password;
    @JsonIgnore
    String roles ="ROLE_USER";


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    public User(@Email(message = "Invalid email address") String email, @NotBlank @Min(value = 5, message = "Password must be atleast five characters") String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getRoles().equals(user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getRoles());
    }

}
