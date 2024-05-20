package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    //инжектим репозиторий
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    public void removeStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> listAge(Integer age) {
        logger.info("Was invoked method for get list by age student");
        return studentRepository.findAll().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());

    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for get list by ageBetween student");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<Student> allStudents() {
        logger.info("Was invoked method for get list student");
        return studentRepository.findAll();
    }

    public Integer getAllStudents() {
        logger.info("Was invoked method for get number of students");
        return studentRepository.getAllStudents();
    }

    public Integer getAvgStudents() {
        logger.info("Was invoked method for get average age from student");
        return studentRepository.getAvgStudents();
    }

    public List<Student> getLastStudents() {
        logger.info("Was invoked method for get list last 5 students");
        return studentRepository.getLastStudents();
    }

    public ResponseEntity<List<String>> namesStartA() {
        List<String> students = studentRepository.findAll().stream()
                .filter(s -> s.getName().toUpperCase().startsWith("A"))
                .map(Student::getName)
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    public ResponseEntity<Double> getAverageAge() {
        double studentsAvAge = studentRepository.findAll().stream()
                .mapToDouble(Student::getAge).average().orElseThrow();
        //.collect(Collectors.toList());
        return ResponseEntity.ok(studentsAvAge);
    }

    public List<String> allParallelNames() {
        List<String> names = studentRepository.findAll().stream()
                .parallel()
                .limit(6)
                .map(Student::getName)
                .toList();
        System.out.println(names.get(0) + " " + names.get(1));
        new Thread(() -> {
            System.out.println(names.get(2) + " " + names.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(names.get(4) + " " + names.get(5));
        }).start();
        return names;
    }

    public List<String> syncNames() {
        List<String> syncStudents = studentRepository.findAll().stream()
                .parallel()
                .limit(6)
                .map(Student::getName)
                .toList();

        sync(syncStudents.get(0));
        sync(syncStudents.get(1));
        new Thread(() -> {
            sync(syncStudents.get(2));
            sync(syncStudents.get(3));
        }).start();
        new Thread(() -> {
            sync(syncStudents.get(4));
            sync(syncStudents.get(5));
        }).start();
        return syncStudents;
    }


    public static void sync(String name) {

        synchronized (StudentService.class) {
            System.out.println("student " + name);

        }

    }
}
