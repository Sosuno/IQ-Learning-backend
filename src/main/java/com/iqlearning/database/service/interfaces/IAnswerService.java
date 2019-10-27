package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;

import java.util.List;


public interface IAnswerService {

    List<Answer> getAnswers(Question q);
    List<Answer> getAnswers(Long q);
    Answer getCorrectAnswer(Question q);
    Answer getCorrectAnswer(Long q);

    Answer saveAnswer(Answer a);

    void deleteAnswer(Answer a);
    void deleteAnswer(Long id);
    void deleteAnswers(Long id);

}
