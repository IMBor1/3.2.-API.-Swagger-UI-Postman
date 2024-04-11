package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
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
        Student student = new Student();
        student.setId(1L);
        student.setAge(22);
        student.setName("Roy");
        assertThat(this.restTemplate.getForObject("http://localhost:"
                + port + "/student/1", Student.class))
                .isEqualTo(studentController.find(1L));
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
