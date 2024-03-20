package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.AlreadeCreatedException;
import ru.hogwarts.school.Exceptions.MyNotFoundException;
import ru.hogwarts.school.Model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    Map<Long, Student> studentMap = new HashMap<>();
    long lastId = 0;

    public Student create(Student student) {
        if (studentMap.containsValue(student)) {
            throw new AlreadeCreatedException();
        }
        student.setId(++lastId);
        studentMap.put(lastId, student);
        return student;
    }

    public Student find(long id) {
        if (!studentMap.containsValue(id)) {
            throw new MyNotFoundException();
        }
        return studentMap.get(id);
    }

    public Student editStudent(Student student) {
        if (!studentMap.containsValue(student)) {
            throw new MyNotFoundException();
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student removeStudent(Long id) {
        if (!studentMap.containsKey(id)) {
            throw new MyNotFoundException();
        }
        return studentMap.remove(id);
    }
}
