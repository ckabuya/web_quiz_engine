package engine.controller;

import engine.exception.QuizNotFoundException;
import engine.exception.UserNotAuthorizedException;
import engine.model.Answer;
import engine.model.Feedback;
import engine.model.Quiz;
import engine.model.User;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RestController
public class QuizController {

    @Autowired
    QuizRepository repository;

    @Autowired
    engine.repository.UserRepository UserRepository;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public QuizController() {
    }

    public QuizController(QuizRepository repository) {
        this.repository = repository;
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json; charset=utf-8")
    public Quiz createQuiz(@RequestBody @NotNull @Valid Quiz answer, @AuthenticationPrincipal Principal principal) {
        System.out.println("/api/quizzes - Posting a quiz ");
        //get current user logged in must be associated with the quiz
        Optional<String> optUser = Optional.ofNullable(principal.getName());
        if (optUser.isPresent()) {
            System.out.println(optUser.get() + " Status of Authentication : ");
            User user = UserRepository.getOne(optUser.get());
            answer.setUser(user);
            System.out.println("User is now associated with quiz " + answer);
            return repository.save(answer);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Must be logged in to create a quiz");
        }

    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        System.out.println("/api/quizzes/{id} - getting quiz by id");
        return repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

    }

    @GetMapping(path = "/api/quizzes")
    public Collection<Quiz> getAllQuizzes() {
        System.out.println("/api/quizzes - Getting all quizzes ");
        Collection<Quiz> r = repository.findAll();
        System.out.println(r);
        return r;
    }

    @PostMapping(value = "/api/quizzes/{id}/solve", consumes = "application/json; charset=utf-8")
    public Feedback solveQuiz(@PathVariable Long id, @RequestBody Answer answer) {
        System.out.println("/api/quizzes/{id}/solve - solving quiz ");
        Feedback feedback;
        Quiz quizFound = repository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
        if (quizFound != null) { //check if the question is available
            System.out.println(quizFound.getAnswer() + " The answer is " + quizFound);
            if ((quizFound.getAnswer() == null && answer.getAnswer().length == 0) || Arrays.equals(quizFound.getAnswer(), answer.getAnswer())) {
                feedback = new Feedback(true, "Congratulations, you're right!");
            } else {
                feedback = new Feedback(false, "Wrong answer! Please, try again.");
            }
            return feedback;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not found"
            );
        }

    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        Optional<String> optUser = Optional.ofNullable(principal.getName());

        if (optUser.isPresent()) {
            //check if quiz exits otherwise throw exception
            Quiz quiz = repository.findById(id)
                    .orElseThrow(() -> new QuizNotFoundException(id));
            //check if the quiz belongs to the logged in user
            if (quiz.getUser().getEmail().equals(optUser.get())) {
                //now delete the quiz
                repository.delete(quiz);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Quiz deleted");
            } else {
                //quiz does not belong to this user
                throw new UserNotAuthorizedException("Not your quiz to delete");
            }
        } else {
            //not logged in
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized to delete. Not your quiz");
        }

    }

}
