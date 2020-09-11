package engine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class QuizController {
    LinkedHashMap<Integer, Quiz> quizDB = new LinkedHashMap<>();
    public QuizController() {
    }


    @PostMapping(path = "/api/quizzes")
    public Quiz createQuiz(@RequestBody Quiz answer) {
        if (answer != null) {
            //answer=2 split on =
            if(quizDB.isEmpty()){
                answer.setId(1);
                quizDB.put(1, answer);
            }
            else{
                int newID = quizDB.size()+1;
                answer.setId(newID);
                quizDB.put(newID, answer);
            }
            System.out.println(answer);
        }
        return new Quiz(answer.getTitle(), answer.getText(), answer.getOptions(), answer.getId());
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        if( quizDB.containsKey(id)){
            Quiz q = quizDB.get(id);
            return new Quiz(q.getTitle(),q.getText(),q.getOptions(),q.getId());
        }
        else{
             throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not found"
            );
        }

    }
    @GetMapping(path = "/api/quizzes")
    public Collection<Quiz> getAllQuizzes() {
        return quizDB.values();
    }
    @PostMapping(path = "/api/quizzes/{id}/solve")
    public Feedback solveQuiz(@PathVariable int id, @RequestBody String answer) {
         Feedback feedback;
         System.out.println("The answer is "+answer);
        if( quizDB.containsKey(id)){ //check if the question is available
            Quiz q = quizDB.get(id);
            String ans = answer.split("=")[1]; // not sure about this but learning
            System.out.println(q);
             if(q.getAnswer() == Integer.parseInt(ans)){
                 feedback = new Feedback(true,"Congratulations, you're right!");
             }
             else{
                 feedback = new Feedback(false,"Wrong answer! Please, try again.");
             }
            return feedback;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not found"
            );
        }

    }
}
