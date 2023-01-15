package id.ac.ui.cs.advancedprogramming.sifost.survey.repository;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScaleQuestionRepository extends JpaRepository<ScaleQuestion, Long> {
    void deleteAllBySurvey(Survey survey);
}
