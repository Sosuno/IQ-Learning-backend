package com.iqlearning.database.service;

import com.iqlearning.database.entities.Subject;
import com.iqlearning.database.repository.SubjectRepository;
import com.iqlearning.database.service.interfaces.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements ISubjectService {

    @Autowired
    private SubjectRepository repo;

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        for (Subject s: repo.findAll()) {
            subjects.add(s);
        }
        return subjects;
    }

    @Override
    public Subject getSubject(Long id) {
        Optional<Subject> o = repo.findById(id);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public List<Subject> getSubjectBy(String name) {
        return repo.getAllByName(name);
    }

    @Override
    public List<Subject> getSubjectBy(int year) {
        return repo.getAllByYear(year);
    }

    @Override
    public Subject getSubjectBy(String name, int year) {
        Optional<Subject> o = repo.getAllByNameAndYear(name,year);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Subject saveSubject(Subject s) {
        return repo.save(s);
    }

    @Override
    public void deleteSubject(Subject s) {
        repo.delete(s);
    }

    @Override
    public void deleteSubject(Long id) {
        repo.deleteById(id);
    }
}
