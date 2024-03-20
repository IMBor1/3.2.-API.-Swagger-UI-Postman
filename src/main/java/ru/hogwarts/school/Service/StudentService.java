package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.StudentAlreadeCreatedException;
import ru.hogwarts.school.Exceptions.StudentNotFoundException;
import ru.hogwarts.school.Model.Student;

import java.util.Map;

@Service
public class StudentService {
    Map<Long, Student> studentMap;
    private long countStudent = 0;

    public Student create(Student student) {
        if (studentMap.containsValue(student)) {
            throw new StudentAlreadeCreatedException();
        }
        Student newStudent = new Student(student.getId(),
                student.getName(), student.getAge());
        countStudent++;
        return newStudent;
    }

    public Student find(Student student) {
        if (!studentMap.containsValue(student)) {
            throw new StudentNotFoundException();
        }
        Student newStudent = new Student(student.getId(),
                student.getName(), student.getAge());
        countStudent++;
        return newStudent;
    }
}
