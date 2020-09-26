package engine.repository;

import engine.model.Quiz;
import engine.model.QuizCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletionRepository extends JpaRepository<QuizCompletion, Long> {

    @Query("SELECT u FROM QuizCompletion u WHERE user = ?1")
    Page<QuizCompletion> findAll(String email,Pageable pageable);
}
