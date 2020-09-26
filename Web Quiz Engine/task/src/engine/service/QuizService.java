package engine.service;

import engine.model.Quiz;
import engine.model.QuizCompletion;
import engine.repository.QuizCompletionRepository;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizCompletionRepository quizCompletionRepository;

    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Quiz> pagedResult = quizRepository.findAll(paging);
        System.out.println("PAGE RESULT "+ pagedResult);//temp
        return pagedResult;
    }
    public Optional<Quiz> getQuiz(Long id){
        return quizRepository.findById(id);
    }
    public Quiz save(Quiz quiz){
        return quizRepository.save(quiz);
    }
    public QuizCompletion save(QuizCompletion quiz){
        return quizCompletionRepository.save(quiz);
    }

    public Page<QuizCompletion> getAllCompletedQuizzes(Integer page, Integer pageSize, String sortBy,String email) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());

        Page<QuizCompletion> pagedResult = quizCompletionRepository.findAll(email,paging);
        System.out.println("PAGE RESULT "+ pagedResult.getTotalElements());//temp
        return pagedResult;
    }
}
