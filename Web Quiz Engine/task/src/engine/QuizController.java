package engine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    public QuizController() {
    }

    @PostMapping(path = "/api/quiz")
    public Feedback quizAnswer(@RequestBody String answer) {
        Feedback feedback = new Feedback(false, "Wrong answer! Please, try again.");
        if (answer != null) {
            //answer=2 split on =
            String ans = answer.split("=")[1]; // not sure about this but learning
            if (ans.equals("2")) {
                feedback = new Feedback(true, "Congratulations, you're right");
            }

        }
        return feedback;
    }

    @GetMapping(path = "/api/quiz")
    public Quiz getTQuiz() {
        String[] opt = {"Robot", "Tea leaf", "Cup of coffee", "Bug"};
        return new Quiz("The Java Logo", "What is depicted on the Java logo?", opt);
    }
}
