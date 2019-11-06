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
        return repo.getAllByOwnerOrderByLastEditedDesc(userId);
    }

    @Override
    public List<Question> getAllSharedQuestionsByUser(Long userId) {
        return repo.getAllByOwnerAndShareableOrderByLastEditedDesc(userId,true);
    }

    @Override
    public List<Question> getAllSharedQuestions() {
        return repo.getAllByShareableOrderByLastEditedDesc(true);
    }

    @Override
    public List<Question> getAllSharedQuestionsForSubject(Subject subject) {
        return repo.getAllBySubjectOrderByLastEditedDesc(subject.getId());
    }

    @Override
    public List<Question> getAllSharedQuestionsForSubject(Long subjectId) {
        return repo.getAllBySubjectOrderByLastEditedDesc(subjectId);
    }

    @Override
    public List<Question> getAllOpenQuestionsByUser(Long userId) {
        return repo.getAllByOwnerAndChoiceTestOrderByLastEditedDesc(userId,false);
    }

    @Override
    public List<Question> getAllOpenQuestionsForSubject(Subject subject) {
        return repo.getAllBySubjectAndChoiceTestOrderByLastEditedDesc(subject.getId(),false);
    }

    @Override
    public List<Question> getAllOpenQuestionsForSubject(Long subjectId) {
        return repo.getAllBySubjectAndChoiceTestOrderByLastEditedDesc(subjectId,false);
    }

    @Override
    public List<Question> getAllClosedQuestionsByUser(Long userId) {
        return repo.getAllByOwnerAndChoiceTestOrderByLastEditedDesc(userId,true);
    }

    @Override
    public List<Question> getAllClosedQuestionsForSubjectForUser(Subject subject, Long userId) {
        return repo.getAllBySubjectAndOwnerAndChoiceTestOrderByLastEditedDesc(subject.getId(),userId,true);
    }

    @Override
    public List<Question> getAllClosedQuestionsForSubjectForUser(Long subjectId, Long userId) {
        return repo.getAllBySubjectAndOwnerAndChoiceTestOrderByLastEditedDesc(subjectId,userId,true);
    }

    @Override
    public List<Question> searchQuestion(String s) {
        return repo.getAllByQuestionContainsOrderByLastEditedDesc(s);
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
        return repo.getAllBySubjectAndOwnerOrderByLastEditedDesc(subject,userId);
    }

    @Override
    public List<Question> getAllShareableOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableOrderByLastEditedDesc(subjectId,userId,true);
    }

    @Override
    public List<Question> getAllNonShareableOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableOrderByLastEditedDesc(subjectId,userId,false);
    }

    @Override
    public List<Question> getAllNonShareableClosedOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,false,true);
    }

    @Override
    public List<Question> getAllNonShareableOpenOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,false,false);
    }

    @Override
    public List<Question> getAllShareableClosedOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,true,true);
    }

    @Override
    public List<Question> getAllShareableOpenOfUserBySubject(Long userId, Long subjectId) {
        return repo.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,true,false);
    }
}
