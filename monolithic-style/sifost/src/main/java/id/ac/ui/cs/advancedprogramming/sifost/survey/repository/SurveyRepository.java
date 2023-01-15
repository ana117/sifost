package id.ac.ui.cs.advancedprogramming.sifost.survey.repository;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
