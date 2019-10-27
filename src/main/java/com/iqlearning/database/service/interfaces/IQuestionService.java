package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Subject;

import java.util.List;

public interface IQuestionService {


    List<Question> getAllUserQuestions(Long userId);
    List<Question> getAllSharedQuestionsByUser(Long userId);
    List<Question> getAllSharedQuestions();
    List<Question> getAllSharedQuestionsForSubject(Subject subject);
    List<Question> getAllSharedQuestionsForSubject(Long subjectId);
    List<Question> getAllOpenQuestionsByUser(Long userId);
    List<Question> getAllOpenQuestionsForSubject(Subject subject);
    List<Question> getAllOpenQuestionsForSubject(Long subjectId);
    List<Question> getAllQuestionForSubjectOfUser(Long userId, Long subject);
    List<Question> getAllClosedQuestionsByUser(Long userId);
    List<Question> getAllClosedQuestionsForSubjectForUser(Subject subject, Long userId);
    List<Question> getAllClosedQuestionsForSubjectForUser(Long subjectId, Long userId);
    List<Question> searchQuestion(String s);
    List<Question> getAllShareableOfUserBySubject(Long userId, Long subjectId);
    List<Question> getAllNonShareableOfUserBySubject(Long userId, Long subjectId);
    List<Question> getAllNonShareableClosedOfUserBySubject(Long userId, Long subjectId);
    List<Question> getAllNonShareableOpenOfUserBySubject(Long userId, Long subjectId);
    List<Question> getAllShareableClosedOfUserBySubject(Long userId, Long subjectId);
    List<Question> getAllShareableOpenOfUserBySubject(Long userId, Long subjectId);


    Question saveQuestion(Question q);

    void deleteQuestion(Question q);
    void deleteQuestion(Long id);
}
