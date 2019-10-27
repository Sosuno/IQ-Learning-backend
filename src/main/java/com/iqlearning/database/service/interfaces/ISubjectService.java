package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();
    Subject getSubject(Long id);
    List<Subject> getSubjectBy(String name);
    List<Subject> getSubjectBy(int year);
    Subject getSubjectBy(String name, int year);

    Subject saveSubject(Subject s);

    void deleteSubject(Subject s);
    void deleteSubject(Long id);
}
