package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.AlreadeCreatedException;
import ru.hogwarts.school.Exceptions.MyNotFoundException;
import ru.hogwarts.school.Model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private HashMap<Long, Student> studentMap = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        if (studentMap.containsValue(student)) {
            throw new AlreadeCreatedException();
        }
        student.setId(++lastId);
        studentMap.put(lastId, student);
        return student;
    }

    public Student findStudent(long id) {
        return studentMap.get(id);
//        if (!studentMap.containsKey(id)) {
//            throw new MyNotFoundException();
//        }
    }

    public Student editStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student removeStudent(long id) {
        if (!studentMap.containsKey(id)) {
            throw new MyNotFoundException();
        }
        return studentMap.remove(id);
    }

    public List<Student> listAge(Integer age) {
        return studentMap.values().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());

    }

}
