package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.Controllers.StudentController;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.AvatarService;
import ru.hogwarts.school.Service.FacultyService;
import ru.hogwarts.school.Service.StudentService;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void saveStudentsTest() throws Exception {
        Long id = 1L;
        String name = "Roy";
        int age = 22;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);

        Student student = new Student();
        student.setName(name);
        student.setId(id);
        student.setAge(age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void findByIdTest() throws Exception {
        Long id = 1L;
        String name = "Roy";
        int age = 22;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        Student student = new Student(id, name, age);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void updateTest() throws Exception {
        Long id = 1L;
        String name = "Roy";
        int age = 22;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        Student student = new Student(id, name, age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void deleteTest() throws Exception {
        Long id = 1L;
        String name = "Roy";
        int age = 22;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        Student student = new Student(id, name, age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTest() throws Exception {
        Long id = 1L;
        String name = "Roy";
        int age = 22;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        Student student = new Student(id, name, age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findAll()).thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        //     .andExpect(jsonPath("$").isArray());
    }
}
