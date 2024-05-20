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

    //создать студента
    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    //найти студента
    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).get();
    }

    //изменить студента
    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    //удалить студента
    public void removeStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    //список студентов с определенным возрастом
    public List<Student> listAge(Integer age) {
        logger.info("Was invoked method for get list by age student");
        return studentRepository.findAll().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());

    }

    //список студентов в промежутке определенного возраста
    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for get list by ageBetween student");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    // Получаем список студентов
    public List<Student> allStudents() {
        logger.info("Was invoked method for get list student");
        return studentRepository.findAll();
    }

    // получаем число всех студентов
    public Integer getAllStudents() {
        logger.info("Was invoked method for get number of students");
        return studentRepository.getAllStudents();
    }

    // получение среднего значения возраста студентов
    public Integer getAvgStudents() {
        logger.info("Was invoked method for get average age from student");
        return studentRepository.getAvgStudents();
    }

    // Получение 5 последних студентов
    public List<Student> getLastStudents() {
        logger.info("Was invoked method for get list last 5 students");
        return studentRepository.getLastStudents();
    }

    // Список имен начинающихся на "А"
    public ResponseEntity<List<String>> namesStartA() {
        List<String> students = studentRepository.findAll().stream()
                .filter(s -> s.getName().toUpperCase().startsWith("A"))
                .map(Student::getName)
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    // Получение среднего значения возраста.
    public ResponseEntity<Double> getAverageAge() {
        double studentsAvAge = studentRepository.findAll().stream()
                .mapToDouble(Student::getAge).average().orElseThrow();
        return ResponseEntity.ok(studentsAvAge);
    }
}
