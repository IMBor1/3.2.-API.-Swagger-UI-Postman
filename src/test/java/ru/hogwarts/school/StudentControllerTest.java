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
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void findStudentByIdGetTest() {
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", new Student(2L, "Rob", 30), Student.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
        Student newStudent = newResponseEntity.getBody();

        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/" + newStudent.getId(), Student.class);

        Student student = responseEntity.getBody();
        assertThat(student.getId().equals(newStudent.getId()));
        assertThat(student.getName().equals(newStudent.getName()));
        assertThat(student.getName().equals(newStudent.getName()));
    }

    @Test
    void createPostTest() throws Exception {
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", new Student(3L, "Rob", 31), Student.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
        Student newStudent = newResponseEntity.getBody();

        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/3", Student.class);

        Student student = responseEntity.getBody();
        assertThat(student.getId().equals(newStudent.getId()));
        assertThat(student.getName().equals(newStudent.getName()));
        assertThat(student.getAge() == (newStudent.getAge()));
    }

    @Test
    void getAllStudentsTest() {
        Student student1 = studentController.createStudent(new Student(4L, "ret", 22));
        Student student2 = studentController.createStudent(new Student(5L, "reto", 23));
        Student student3 = studentController.createStudent(new Student(6L, "reta", 22));
        ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student", ArrayList.class);
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertThat(responseEntity.getBody().size() == 3);

    }
    @Test
    void getStudentsByAgeTest() {
        // ObjectMapper objectMapper = new ObjectMapper();
        Student student1 = studentController.createStudent(new Student(0L, "ret", 22));
        Student student2 = studentController.createStudent(new Student(5L, "ret", 42));
        ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/age?age=" + 22, ArrayList.class);
        ArrayList<Student> student = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
        //  assertThat(student.get(0).getAge() == 22);
    }

    @Test
    public void deleteStudentTest() throws Exception {
        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/student", new Student(2L, "Rob", 30), Student.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
        Student newStudent = newResponseEntity.getBody();
        restTemplate.delete("http://localhost:" +
                port + "/student/" + newStudent.getId(), Student.class);
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student/" + newStudent.getId(), Student.class);
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    //   @Test
//    void editStudentPut() {
//        ResponseEntity<Student> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
//                port + "/student", new Student(2L, "Rob", 30), Student.class);
//        Student student = newResponseEntity.getBody();
//        HttpEntity<Student> requestEntity = new RequestEntity<>(student, PUT, null);
//        student.setAge(23);
//        student.setName("qqq");
//        ResponseEntity<Student> responseEntity = restTemplate.exchange("http://localhost:" +
//                port + "/student", student.getId(), PUT,requestEntity, Student.class);
//        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));

    //   }

    @Test
    void betweenAgeGetTest() {
        Student student1 = studentController.createStudent(new Student(0L, "ret", 22));
        Student student2 = studentController.createStudent(new Student(5L, "ret", 42));
        Student student3 = studentController.createStudent(new Student(6L, "ree", 30));
        ResponseEntity<Collection> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/student?min=" + 23 + "&max=" + 39, Collection.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody().size() == 1);
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
