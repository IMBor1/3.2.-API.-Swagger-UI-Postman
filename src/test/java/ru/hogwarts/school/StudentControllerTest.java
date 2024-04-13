package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.Controllers.StudentController;
import ru.hogwarts.school.Model.Student;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void findStudentByIdGetTest() {
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", new Student(1L, "Rob", 30), Student.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
        Student newStudent = newResponseEntity.getBody();

        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "student" + newStudent.getId(), Student.class);

        Student student = responseEntity.getBody();
        assertThat(student.getId().equals(newStudent.getId()));
        assertThat(student.getName().equals(newStudent.getName()));
        assertThat(student.getAge().
    }

    @Test
    void createPostTest() throws Exception {
        Student student = new Student();
        student.setId(2L);
        student.setAge(20);
        student.setName("Ray");
        assertThat(this.restTemplate.postForObject("http://localhost:" +
                port + "/student", student, Student.class))
                .isEqualTo(student);

        assertThat(student.getName().equals("Ray"));
    }

    @Test
    void getAllStudentsTest() {
        assertThat(this.restTemplate.getForObject("http://localhost:"
                + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    void getStudentsByAgeTest() {
        assertThat(this.restTemplate.getForObject("http://localhost:"
                + port + "/student/20", String.class))
                .isNotNull();
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Student student = new Student();
        student.setId(2L);
        student.setAge(20);
        student.setName("Ray");
        this.restTemplate.delete("http://localhost:" +
                port + "/student/" + student.getId(), String.class);

    }

    @Test
    void editStudentPut() {
        assertThat(this.restTemplate.getForObject("http://localhost:"
                + port + "/student/1", String.class))
                .isNotNull();
    }

    @Test
    void betweenAgeGetTest() {
        assertThat(this.restTemplate.getForObject("http://localhost:"
                + port + "/student/10,20", String.class))
                .isNotNull();
    }

    @Test
    void getStudentByFaculty() {
        Student student = new Student();
        student.setId(3L);
        student.setAge(22);
        student.setName("Rak");
        assertThat(this.restTemplate.getForObject("http://localhost:"
                + port + "/student/1/faculty", String.class))
                .isEqualTo(new Student(5L, "tok", 33));
    }
}
