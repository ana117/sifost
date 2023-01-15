package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.resulthandler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;

public interface ResultHandler {

    Survey handle(Survey survey);

    void setNextHandler(ResultHandler handler);
}
