package ru.hogwarts.school.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class AllStudentsController {
    StudentService studentService;

    public AllStudentsController(StudentService studentService) {
        this.studentService = studentService;
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
}
