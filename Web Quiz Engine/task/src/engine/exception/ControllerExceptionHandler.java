package engine.exception;

import engine.exception.QuizNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final String NOT_FOUND_MESSAGE = "Information not found for number";
    private static final String BAD_REQUEST = "Bad request";

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(code =HttpStatus.NOT_FOUND, reason = NOT_FOUND_MESSAGE)
    public HashMap<String, String> handleIndexOutOfBoundsException(Exception e) {
        System.out.println("EXCEPTION ERROR!!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", NOT_FOUND_MESSAGE);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(code =HttpStatus.NOT_FOUND, reason = NOT_FOUND_MESSAGE)
    public HashMap<String, String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        System.out.println("USER EXCEPTION ERROR!!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", NOT_FOUND_MESSAGE);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseStatus(code =HttpStatus.NOT_FOUND, reason = NOT_FOUND_MESSAGE)
    public HashMap<String, String> handleQuizNotFoundException(QuizNotFoundException e) {
        System.out.println("EXCEPTION ERROR!!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", NOT_FOUND_MESSAGE);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(code =HttpStatus.FORBIDDEN, reason = "Not your quiz to delete")
    public HashMap<String, String> handleUserNotAuthorizedException(UserNotAuthorizedException e) {
        System.out.println("UserNotAuthorizedException ERROR!!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Not your quiz to delete");
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HashMap<String, String> handleConstraintViolationException(ConstraintViolationException e) {
        System.out.println("EXCEPTION ERROR!!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", BAD_REQUEST);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HashMap<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("EXCEPTION ERROR!!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HashMap<String, String> handleAnyException(Exception e) {
        System.out.println("UNKNOWN ERROR !!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase()+ " : "+e.getMessage());
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HashMap<String, String> handleUserNotFoundException(UserNotFoundException e) {
        System.out.println("USER NOT FOUND !!!! "+e);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase()+ " : "+e.getMessage());
        response.put("error", e.getClass().getSimpleName());
        return response;
    }
}
