package engine.controller;

import engine.exception.QuizNotFoundException;
import engine.exception.UserNotAuthorizedException;
import engine.model.*;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
import java.util.Date;
import java.util.Optional;

@RestController
public class QuizController {

    @Autowired
    QuizRepository repository;

    @Autowired
    engine.repository.UserRepository UserRepository;

    @Autowired
    QuizService quizService;

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
            return quizService.save(answer);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Must be logged in to create a quiz");
        }

    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        System.out.println("/api/quizzes/{id} - getting quiz by id");
        return quizService.getQuiz(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

    }
    /*@GetMapping(path = "/api/quizzes")
    public Collection<Quiz> getAllQuizzes() {

        System.out.println("/api/quizzes - Getting all quizzes ");
        Collection<Quiz> r = repository.findAll();
        System.out.println(r);
        return r;
    }*/

    @GetMapping(path = "/api/quizzes")
    public ResponseEntity getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        Page<Quiz> list = quizService.getAllQuizzes(page, pageSize, sortBy);

        return new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping(path = "/api/quizzes/completed")
    public ResponseEntity getAllCompletedQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "completedAt") String sortBy,@AuthenticationPrincipal Principal principal)
    {
        User user = checkLoginStatus(principal);//user
        Page<QuizCompletion> list;
        list = quizService.getAllCompletedQuizzes(page, pageSize, sortBy,user.getEmail());

        return new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/api/quizzes/{id}/solve", consumes = "application/json; charset=utf-8")
    public Feedback solveQuiz(@PathVariable Long id, @RequestBody Answer answer,@AuthenticationPrincipal Principal principal) {
        System.out.println("/api/quizzes/{id}/solve - solving quiz ");

        User user = checkLoginStatus(principal);//user
        //all is well
        Feedback feedback;
        Quiz quizFound = repository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
        if (quizFound != null) { //check if the question is available
            System.out.println(quizFound.getAnswer() + " The answer is " + quizFound);
            if ((quizFound.getAnswer() == null && answer.getAnswer().length == 0) || Arrays.equals(quizFound.getAnswer(), answer.getAnswer())) {
                feedback = new Feedback(true, "Congratulations, you're right!");

                QuizCompletion quizCompletion=new QuizCompletion();
                quizCompletion.setCompletedAt(new Date(System.currentTimeMillis()));
                quizCompletion.setUser(user.getEmail());
                quizCompletion.setId(id);
                quizService.save(quizCompletion);


            } else {
                feedback = new Feedback(false, "Wrong answer! Please, try again.");
            }


            //save that question was taken here
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
    private User checkLoginStatus(Principal principal){
        Optional<String> optUser = Optional.ofNullable(principal.getName());
        User user =null;
        if (optUser.isPresent()) { //check if user is authenticated
            System.out.println(optUser.get() + " Status of Authentication : ");
            user = UserRepository.getOne(optUser.get());
        }
        else {
            throw new UserNotAuthorizedException("Must be logged in to create a quiz");
        }
        return user;
    }

}
