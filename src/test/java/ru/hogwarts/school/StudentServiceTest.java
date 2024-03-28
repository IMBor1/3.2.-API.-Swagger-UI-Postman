package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    StudentRepository mockRepository;
    @InjectMocks
    StudentService studentService;

    Student student1 = new Student(1L, "history", 22);
    Student student2 = new Student(2L, "history", 22);
    Student student3 = new Student(3L, "math", 33);
    List<Student> student = new ArrayList<>(List.of(student1, student2, student3));
    List<Student> studentAge = new ArrayList<>(List.of(student1, student2));

    @Test
    void createstudentByPostegree() {
        when(mockRepository.save(new Student(1L, "history", 22)))
                .thenReturn(student1);
        assertEquals(student1, studentService.createStudent(new Student(1L,
                "history", 22)));
    }

    @Test
    void findStudentByPostegree() {
        when(mockRepository.findById(1L))
                .thenReturn(Optional.ofNullable(student1));
        assertEquals(student1, studentService.findStudent(1L));
    }

    @Test
    void editStudentByPostegree() {

        when(mockRepository.save(new Student(1L, "history", 22)))
                .thenReturn(student2);
        assertEquals(student2, studentService.editStudent(new Student(1L,
                "history", 22)));
    }

    @Test
    void getAge() {
        when(mockRepository.findAll())
                .thenReturn(student);
        assertEquals(studentAge, studentService.listAge(22));
    }
}
