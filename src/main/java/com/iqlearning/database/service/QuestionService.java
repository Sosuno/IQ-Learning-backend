package com.iqlearning.database.service;

import com.iqlearning.database.utils.FilledQuestion;
import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Subject;
import com.iqlearning.database.repository.AnswerRepository;
import com.iqlearning.database.repository.QuestionRepository;
import com.iqlearning.database.service.interfaces.IAnswerService;
import com.iqlearning.database.service.interfaces.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService, IAnswerService {

    private SubjectService subjectService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    public void setMissionService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public Question get(Long questionId) {
        Optional<Question> o = questionRepository.findById(questionId);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public List<Question> getAllUserQuestions(Long userId) {
        return questionRepository.getAllByOwnerOrderByLastEditedDesc(userId);
    }

    @Override
    public List<Question> getAllSharedQuestionsByUser(Long userId) {
        return questionRepository.getAllByOwnerAndShareableOrderByLastEditedDesc(userId,true);
    }

    @Override
    public List<Question> getAllSharedQuestions() {
        return questionRepository.getAllByShareableOrderByLastEditedDesc(true);
    }

    @Override
    public List<Question> getAllSharedQuestionsForSubject(Subject subject) {
        return questionRepository.getAllBySubjectOrderByLastEditedDesc(subject.getId());
    }

    @Override
    public List<Question> getAllSharedQuestionsForSubject(Long subjectId) {
        return questionRepository.getAllBySubjectOrderByLastEditedDesc(subjectId);
    }

    @Override
    public List<Question> getAllOpenQuestionsByUser(Long userId) {
        return questionRepository.getAllByOwnerAndChoiceTestOrderByLastEditedDesc(userId,false);
    }

    @Override
    public List<Question> getAllOpenQuestionsForSubject(Subject subject) {
        return questionRepository.getAllBySubjectAndChoiceTestOrderByLastEditedDesc(subject.getId(),false);
    }

    @Override
    public List<Question> getAllOpenQuestionsForSubject(Long subjectId) {
        return questionRepository.getAllBySubjectAndChoiceTestOrderByLastEditedDesc(subjectId,false);
    }

    @Override
    public List<Question> getAllClosedQuestionsByUser(Long userId) {
        return questionRepository.getAllByOwnerAndChoiceTestOrderByLastEditedDesc(userId,true);
    }

    @Override
    public List<Question> getAllClosedQuestionsForSubjectForUser(Subject subject, Long userId) {
        return questionRepository.getAllBySubjectAndOwnerAndChoiceTestOrderByLastEditedDesc(subject.getId(),userId,true);
    }

    @Override
    public List<Question> getAllClosedQuestionsForSubjectForUser(Long subjectId, Long userId) {
        return questionRepository.getAllBySubjectAndOwnerAndChoiceTestOrderByLastEditedDesc(subjectId,userId,true);
    }

    @Override
    public List<Question> searchQuestion(String s) {
        return questionRepository.getAllByQuestionContainsOrderByLastEditedDesc(s);
    }

    @Override
    public Question saveQuestion(Question q) {
        return questionRepository.save(q);
    }

    @Override
    public void deleteQuestion(Question q) {
        q.setShareable(false);
        q.setOwner(1L);
        questionRepository.save(q);
    }

    @Override
    public void deleteQuestion(Long id) {
        Question q = get(id);
        q.setShareable(false);
        q.setOwner(1L);
        questionRepository.save(q);
    }

    @Override
    public List<Question> getAllQuestionForSubjectOfUser(Long userId, Long subject) {
        return questionRepository.getAllBySubjectAndOwnerOrderByLastEditedDesc(subject,userId);
    }

    @Override
    public List<Question> getAllShareableOfUserBySubject(Long userId, Long subjectId) {
        return questionRepository.getAllBySubjectAndOwnerAndShareableOrderByLastEditedDesc(subjectId,userId,true);
    }

    @Override
    public List<Question> getAllNonShareableOfUserBySubject(Long userId, Long subjectId) {
        return questionRepository.getAllBySubjectAndOwnerAndShareableOrderByLastEditedDesc(subjectId,userId,false);
    }

    @Override
    public List<Question> getAllNonShareableClosedOfUserBySubject(Long userId, Long subjectId) {
        return questionRepository.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,false,true);
    }

    @Override
    public List<Question> getAllNonShareableOpenOfUserBySubject(Long userId, Long subjectId) {
        return questionRepository.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,false,false);
    }

    @Override
    public List<Question> getAllShareableClosedOfUserBySubject(Long userId, Long subjectId) {
        return questionRepository.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,true,true);
    }

    @Override
    public List<Question> getAllShareableOpenOfUserBySubject(Long userId, Long subjectId) {
        return questionRepository.getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(subjectId,userId,true,false);
    }

    @Override
    public List<Answer> getAnswers(Question q) {
        return answerRepository.getAllByQuestionId(q.getId());
    }

    @Override
    public List<Answer> getAnswers(Long q) {
        return answerRepository.getAllByQuestionId(q);
    }

    @Override
    public Answer getCorrectAnswer(Question q) {
        Optional<Answer> o = answerRepository.getAllByQuestionIdAndCorrect(q.getId(),true);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Answer getCorrectAnswer(Long q) {
        Optional<Answer> o = answerRepository.getAllByQuestionIdAndCorrect(q,true);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Answer saveAnswer(Answer a) {
        return answerRepository.save(a);
    }

    @Override
    public void deleteAnswer(Answer a) {
        answerRepository.delete(a);
    }

    @Override
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public void deleteAnswers(Long id) {
        answerRepository.deleteAllByQuestionId(id);
    }

    public Question addQuestion(FilledQuestion q) {
        Question toDbQ = new Question(q.getOwner(),q.getSubject().getId(),q.getQuestion(),q.isChoiceTest(),q.isShareable(), q.getCreated(), q.getLastEdited());
        toDbQ.setLastEdited(new Timestamp(System.currentTimeMillis()));
        if(q.getId() != null) toDbQ.setId(q.getId());
        else toDbQ.setCreated(new Timestamp(System.currentTimeMillis()));
        toDbQ = saveQuestion(toDbQ);

        if(q.isChoiceTest()){
            if(q.getId() != null) {
                deleteAnswers(q.getId());
            }
            List<Answer> answers = q.getAnswers();
            for(Answer a: answers){
                a.setQuestion_id(toDbQ.getId());
                a.setCreated(new Timestamp(System.currentTimeMillis()));
                a.setLastEdited(new Timestamp(System.currentTimeMillis()));
                saveAnswer(a);
            }
        }
        return toDbQ;
    }

    public List<FilledQuestion> getReadyQuestions(List<Question> questions) {
        List<FilledQuestion> readyList = new ArrayList<>();
        FilledQuestion question;
        for (Question q: questions){
            question = new FilledQuestion(q);
            if(q.isChoice_test()) question.setAnswers(getAnswers(q));
            question.setSubject(subjectService.getSubject(q.getSubject()));
            readyList.add(question);
        }
        return readyList;
    }

    public FilledQuestion getReadyQuestion(Question q) {
        FilledQuestion readyQuestion = new FilledQuestion(q);
        if(q.isChoice_test()) readyQuestion.setAnswers(getAnswers(q));
        readyQuestion.setSubject(subjectService.getSubject(q.getSubject()));
        return readyQuestion;
    }
}
