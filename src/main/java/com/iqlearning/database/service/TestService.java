package com.iqlearning.database.service;

import com.iqlearning.database.entities.Test;
import com.iqlearning.database.repository.TestRepository;
import com.iqlearning.database.service.interfaces.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService implements ITestService{

    @Autowired
    TestRepository repo;

    @Override
    public Test getTest(Long id) {
        Optional<Test> o = repo.findById(id);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public List<Test> getTestsByUserAndSubject(Long userId, Long subjectId) {
        return repo.getAllByOwnerAndSubjectOrderByLastEditedDesc(userId, subjectId);
    }

    @Override
    public List<Test> getTestsByUser(Long id) {
        return repo.getAllByOwnerOrderByLastEditedDesc(id);
    }

    @Override
    public List<Test> getSharedTestsBySubject(Long subjectId) {
        return repo.getAllBySubjectAndShareableOrderByLastEditedDesc(subjectId, true);
    }

    @Override
    public List<Test> getSharedTests() {
        return repo.getAllByShareableOrderByLastEdited(true);
    }

    @Override
    public Test saveTest(Test test) {
        return repo.save(test);
    }

    @Override
    public void deleteTest(Test test) {
        repo.delete(test);

    }

    @Override
    public void deleteTest(Long id) {
        repo.deleteById(id);
    }
}
