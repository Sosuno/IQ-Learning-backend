package com.iqlearning.database.service;

import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Subject;
import com.iqlearning.database.repository.QuestionRepository;
import com.iqlearning.database.service.interfaces.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionRepository repo;

    @Override
    public List<Question> getAllUserQuestions(Long userId) {
        return repo.getAllByOwner(userId);
    }

    @Override
    public List<Question> getAllSharedQuestionsByUser(Long userId) {
        return repo.getAllByOwnerAndShareable(userId,true);
    }

    @Override
    public List<Question> getAllSharedQuestions() {
        return repo.getAllByShareable(true);
    }

    @Override
    public List<Question> getAllSharedQuestionsForSubject(Subject subject) {
        return repo.getAllBySubject(subject.getId());
    }

    @Override
    public List<Question> getAllSharedQuestionsForSubject(Long subjectId) {
        return repo.getAllBySubject(subjectId);
    }

    @Override
    public List<Question> getAllOpenQuestionsByUser(Long userId) {
        return repo.getAllByOwnerAndChoiceTest(userId,false);
    }

    @Override
    public List<Question> getAllOpenQuestionsForSubject(Subject subject) {
        return repo.getAllBySubjectAndChoiceTest(subject.getId(),false);
    }

    @Override
    public List<Question> getAllOpenQuestionsForSubject(Long subjectId) {
        return repo.getAllBySubjectAndChoiceTest(subjectId,false);
    }

    @Override
    public List<Question> getAllClosedQuestionsByUser(Long userId) {
        return repo.getAllByOwnerAndChoiceTest(userId,true);
    }

    @Override
    public List<Question> getAllClosedQuestionsForSubjectForUser(Subject subject, Long userId) {
        return repo.getAllBySubjectAndOwnerAndChoiceTest(subject.getId(),userId,true);
    }

    @Override
    public List<Question> getAllClosedQuestionsForSubjectForUser(Long subjectId, Long userId) {
        return repo.getAllBySubjectAndOwnerAndChoiceTest(subjectId,userId,true);
    }

    @Override
    public List<Question> searchQuestion(String s) {
        return repo.getAllByQuestionContains(s);
    }

    @Override
    public Question saveQuestion(Question q) {
        return repo.save(q);
    }

    @Override
    public void deleteQuestion(Question q) {
        repo.delete(q);
    }

    @Override
    public void deleteQuestion(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Question> getAllQuestionForSubjectOfUser(Long userId, Long subject) {
        return repo.getAllBySubjectAndOwner(subject,userId);
    }

    @Override
    public List<Question> getAllShareableOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareable(subjectId,userId,true);
    }

    @Override
    public List<Question> getAllNonShareableOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareable(subjectId,userId,false);
    }

    @Override
    public List<Question> getAllNonShareableClosedOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTest(subjectId,userId,false,true);
    }

    @Override
    public List<Question> getAllNonShareableOpenOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTest(subjectId,userId,false,false);
    }

    @Override
    public List<Question> getAllShareableClosedOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTest(subjectId,userId,true,true);
    }

    @Override
    public List<Question> getAllShareableOpenOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTest(subjectId,userId,true,false);
    }
}
