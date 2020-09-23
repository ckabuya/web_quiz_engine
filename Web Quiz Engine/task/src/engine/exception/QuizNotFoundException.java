package engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(Long id) {
        super("Could not find quiz: " + id);
    }
}
