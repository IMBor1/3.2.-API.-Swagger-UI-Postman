package ru.hogwarts.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.Controllers.FacultyController;
import ru.hogwarts.school.Controllers.StudentController;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;
    Faculty faculty;

    @BeforeEach
    void init() {
        avatarRepository.deleteAll();
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
        Faculty faculty = new Faculty(1L, "history", "red");
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", faculty, Faculty.class);
        this.faculty = facultyResponseEntity.getBody();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void findStudentByIdGetTest() {
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", new Student(2L, "Rob", 30), Student.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        Student newStudent = newResponseEntity.getBody();

        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/" + newStudent.getId(), Student.class);

        Student student = responseEntity.getBody();
        assertThat(student.getId().equals(newStudent.getId())).isTrue();
        assertThat(student.getName().equals(newStudent.getName())).isTrue();
        assertThat(student.getAge() == (newStudent.getAge())).isTrue();
    }

    @Test
    void createPostTest() throws Exception {
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", new Student(3L, "Rob", 31), Student.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        Student newStudent = newResponseEntity.getBody();

        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/" + newStudent.getId(), Student.class);

        Student student = responseEntity.getBody();
        assertThat(student.getId().equals(newStudent.getId())).isTrue();
        assertThat(student.getName().equals(newStudent.getName())).isTrue();
        assertThat(student.getAge() == (newStudent.getAge())).isTrue();
    }

    @Test
    void getAllStudentsTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student1 = studentController.createStudent(new Student(4L, "ret", 22));
        Student student2 = studentController.createStudent(new Student(5L, "reto", 23));
        Student student3 = studentController.createStudent(new Student(6L, "reta", 22));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student", String.class);
        List<Student> students = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        assertThat(students.size() == 3).isTrue();

    }



    @Test
    public void deleteStudentTest() throws Exception {
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", new Student(2L, "Rob", 30), Student.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        Student newStudent = newResponseEntity.getBody();
        restTemplate.delete("http://localhost:" +
                port + "/student/" + newStudent.getId(), Student.class);
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/" + newStudent.getId(), Student.class);
        assertThat(responseEntity.getBody()).isNotEqualTo(newStudent);
    }

    @Test
    void editStudentPut() {
        Student newStudent = new Student(2L, "Rob", 30);
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", newStudent, Student.class);
        Student student = newResponseEntity.getBody();
        HttpEntity<Student> requestEntity = new RequestEntity<>(student, HttpMethod.PUT, null);
        student.setAge(23);
        student.setName("qqq");
        ResponseEntity<Student> responseEntity = restTemplate.exchange("http://localhost:" +
                port + "/student/" + student.getId(), PUT, requestEntity, Student.class);
        Student student1 = responseEntity.getBody();
        assertThat(student1.equals(student)).isTrue();

    }

    @Test
    void betweenAgeGetTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student1 = studentController.createStudent(new Student(0L, "ret", 22));
        Student student2 = studentController.createStudent(new Student(5L, "ret", 42));
        Student student3 = studentController.createStudent(new Student(6L, "ree", 30));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/ageBetween?minAge=25&maxAge=40", String.class);
        List<Student> studentObject = objectMapper.readValue(responseEntity.getBody(),
                new TypeReference<>() {
                });
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        assertThat(studentObject.size() == 1).isTrue();
    }


    @Test
    void getFacultyByStudent() {
        Faculty faculty1 = facultyController.createFaculty(new Faculty(1L, "history", "red"));
        Student newStudent = new Student(1L, "Rob", 30);
        newStudent.setFaculty(faculty1);
        Student studentDb = studentController.createStudent(newStudent);
        ResponseEntity<Faculty> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/" + studentDb.getId() + "/faculty", Faculty.class);
        Faculty faculty = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        assertThat(faculty1.getName().equals(faculty.getName())).isTrue();
    }

    @Test
    void getStudentsByAgeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student1 = studentController.createStudent(new Student(0L, "ret", 22));
        Student student2 = studentController.createStudent(new Student(5L, "ret", 42));
        Student student3 = studentController.createStudent(new Student(6L, "ret", 22));
        // HttpEntity<Student> requestEntity = new RequestEntity<>(student1, HttpMethod.PUT, null);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/age?age=22", String.class);
        List<Student> studentList = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        assertThat(studentList.size() == 2).isTrue();
    }
}
