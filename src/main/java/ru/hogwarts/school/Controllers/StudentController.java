package ru.hogwarts.school.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity find(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping()
    public List<Student> listAllStudents() {
        return studentService.allStudents();
    }

    @GetMapping("/age")
    public List<Student> listStudentsByAge(@RequestParam Integer age) {
        return studentService.listAge(age);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity editStudent(@PathVariable Long id, @RequestBody Student student) {
        Student student1 = studentService.editStudent(student);
        if (student1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ageBetween")
    public List<Student> findByAgeBetween(@RequestParam int minAge,
                                          @RequestParam int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity findFacultyByStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getFaculty());
    }

    @GetMapping("/allNumber")
    public Integer getAllStudentsBySchool() {
        return studentService.getAllStudents();
    }

    @GetMapping("/averageAge")
    Integer getAvgStudents() {
        return studentService.getAvgStudents();
    }

    @GetMapping("/last5Id")
    List<Student> getLastStudents() {
        return studentService.getLastStudents();
    }

    @GetMapping("/namesStartA")
    public ResponseEntity<List<String>> namesStartA() {
        return studentService.namesStartA();
    }

    @GetMapping("/getAverageAge")
    public ResponseEntity<Double> getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("print-parallel")
    public List<String> allParallelNames() {
        return studentService.allParallelNames();
    }

    @GetMapping("print-synchronized")
    public List<String> syncNames() {
        return studentService.syncNames();
    }
}
