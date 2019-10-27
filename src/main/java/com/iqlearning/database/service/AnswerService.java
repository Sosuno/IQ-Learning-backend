package com.iqlearning.database.service;

import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.repository.AnswerRepository;
import com.iqlearning.database.service.interfaces.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AnswerService implements IAnswerService {

    @Autowired
    private AnswerRepository repo;

    @Override
    public List<Answer> getAnswers(Question q) {
        return repo.getAllByQuestionId(q.getId());
    }

    @Override
    public List<Answer> getAnswers(Long q) {
        return repo.getAllByQuestionId(q);
    }

    @Override
    public Answer getCorrectAnswer(Question q) {
        Optional<Answer> o = repo.getAllByQuestionIdAndCorrect(q.getId(),true);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Answer getCorrectAnswer(Long q) {
        Optional<Answer> o = repo.getAllByQuestionIdAndCorrect(q,true);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Answer saveAnswer(Answer a) {
        return repo.save(a);
    }

    @Override
    public void deleteAnswer(Answer a) {
        repo.delete(a);
    }

    @Override
    public void deleteAnswer(Long id) {
        repo.deleteById(id);
    }
}
