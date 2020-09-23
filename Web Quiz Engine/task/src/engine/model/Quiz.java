package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.*;
@JsonIgnoreProperties(value = "answer", allowSetters  = true)
@Entity
public class Quiz {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;
    @NotBlank(message = "title is required")
    String title;

    @NotBlank(message = "text is required")
    String text;
    @NotNull
    @Size(min=2)
    String[] options;

    int [] answer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User user;

    public Quiz(String title, String text, String[] options) {
        this.title = title;
        this.text = text;
        this.options = options;
    }
    public Quiz(String title, String text, String[] options,int [] answer,long id) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer == null ? new int[]{} : answer;
        this.id = id;
    }
    public Quiz(String title, String text, String[] options,long id) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.id = id;
    }
    public Quiz() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int [] answer) {
        this.answer = answer == null ? new int[]{} : answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        Quiz quiz = (Quiz) o;
        return getId() == quiz.getId() &&
                getTitle().equals(quiz.getTitle()) &&
                getText().equals(quiz.getText()) &&
                Arrays.equals(getOptions(), quiz.getOptions()) &&
                Arrays.equals(getAnswer(), quiz.getAnswer());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getTitle(), getText());
        result = 31 * result + Arrays.hashCode(getOptions());
        result = 31 * result + Arrays.hashCode(getAnswer());
        return result;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + Arrays.toString(options) +
                ", answer=" + Arrays.toString(answer) +
                '}';
    }

}
