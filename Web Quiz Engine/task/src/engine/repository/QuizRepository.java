package engine.repository;

import engine.model.Quiz;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface QuizRepository extends CrudRepository<Quiz,Long> {
       Quiz findById(long id);
       Collection<Quiz> findAll();
}
