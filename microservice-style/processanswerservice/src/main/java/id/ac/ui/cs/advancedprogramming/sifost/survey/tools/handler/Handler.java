package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;

import java.util.List;

public interface Handler {
    Survey handle(List<String> answers, Survey survey);
    void setNextHandler(Handler handler);
}
