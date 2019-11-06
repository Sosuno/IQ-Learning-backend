package com.iqlearning.context.activities;


import com.iqlearning.context.objects.FilledQuestion;
import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.service.interfaces.IAnswerService;
import com.iqlearning.database.service.interfaces.IQuestionService;
import com.iqlearning.database.service.interfaces.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Configurable
public class QuestionsManagement {

    private final IQuestionService questionService;
    private final IAnswerService answerService;
    private final ISubjectService subjectService;

    @Autowired
    public QuestionsManagement(IQuestionService questionService, IAnswerService answerService, ISubjectService subjectService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.subjectService = subjectService;
    }

    public Question addQuestion(FilledQuestion q) {
        Question toDbQ = new Question(q.getOwner(),q.getSubject().getId(),q.getQuestion(),q.isChoiceTest(),q.isShareable());
        toDbQ.setLastEdited(new Timestamp(System.currentTimeMillis()));
        if(q.getId() != null) {
            toDbQ.setId(q.getId());
        }else {
            toDbQ.setCreated(new Timestamp(System.currentTimeMillis()));
        }
        toDbQ = questionService.saveQuestion(toDbQ);

        if(q.isChoiceTest()){
            List<Answer> answers = q.getAnswers();
            for(Answer a: answers){
                a.setQuestion_id(toDbQ.getId());
                answerService.saveAnswer(a);
            }
        }
        return toDbQ;
    }
    public List<FilledQuestion> getReadyQuestions(List<Question> questions) {
        List<FilledQuestion> readyList = new ArrayList<>();
        FilledQuestion question;
        for (Question q: questions){
            question = new FilledQuestion(q);
            if(q.isChoice_test()) question.setAnswers(answerService.getAnswers(q));
            question.setSubject(subjectService.getSubject(q.getSubject()));
            readyList.add(question);
        }
        return readyList;
    }

    public FilledQuestion getReadyQuestion(Question q) {
        FilledQuestion readyQuestion = new FilledQuestion(q);
        if(q.isChoice_test()) readyQuestion.setAnswers(answerService.getAnswers(q));
        readyQuestion.setSubject(subjectService.getSubject(q.getSubject()));
        return readyQuestion;
    }

    /***
     * Careful when statistics are added!!!!
     * Will need rework
     * TODO
     */
    public void deleteQuestion(Long q) {
        answerService.deleteAnswers(q);
        questionService.deleteQuestion(q);
    }

}
