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
public class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @BeforeEach
    void init() {
        avatarRepository.deleteAll();
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void createFacultyPostTest() throws Exception {
        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", new Faculty(1L, "math", "black"), Faculty.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        Faculty newFaculty = newResponseEntity.getBody();

        ResponseEntity<Faculty> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Faculty faculty = responseEntity.getBody();
        assertThat(faculty.getId().equals(newFaculty.getId())).isTrue();
        assertThat(faculty.getName().equals(newFaculty.getName())).isTrue();
        assertThat(faculty.getColor().equals(newFaculty.getColor())).isTrue();
    }

    @Test
    void findFacultyByIdGetTest() {
        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", new Faculty(2L, "music", "blue"), Faculty.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        Faculty newFaculty = newResponseEntity.getBody();

        ResponseEntity<Faculty> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Faculty faculty = responseEntity.getBody();
        assertThat(faculty.getId().equals(newFaculty.getId())).isTrue();
        assertThat(faculty.getName().equals(newFaculty.getName())).isTrue();
        assertThat(faculty.getColor().equals(newFaculty.getColor())).isTrue();
    }


    @Test
    public void deleteFacultyTest() throws Exception {
        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", new Faculty(2L, "math", "red"), Faculty.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
        Faculty newFaculty = newResponseEntity.getBody();
        restTemplate.delete("http://localhost:" +
                port + "/faculty/" + newFaculty.getId(), Faculty.class);
        ResponseEntity<Faculty> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/" + newFaculty.getId(), Faculty.class);
        assertThat(responseEntity.getStatusCode()).isNotEqualTo(newFaculty);
    }

    @Test
    void editFacultyPut() {
        Faculty newFaculty = new Faculty(1L, "math", "black");
        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", newFaculty, Faculty.class);
        Faculty faculty = newResponseEntity.getBody();
        HttpEntity<Faculty> requestEntity = new RequestEntity<>(faculty, HttpMethod.PUT, null);
        faculty.setColor("pink");
        faculty.setName("ooo");
        ResponseEntity<Faculty> responseEntity = restTemplate.exchange("http://localhost:" +
                port + "/faculty/" + faculty.getId(), PUT, requestEntity, Faculty.class);
        Faculty faculty1 = responseEntity.getBody();
        assertThat(faculty1.equals(faculty)).isTrue();

    }

    @Test
    void findByNameIgnoreCaseOrColorIgnoreCaseGetTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Faculty faculty1 = facultyController.createFaculty(new Faculty(0L, "math", "red"));
        Faculty faculty2 = facultyController.createFaculty(new Faculty(5L, "history", "white"));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/nameOrColor?name=math&color=ww", String.class);
        List<Faculty> faculty = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        assertThat(faculty.get(0).getName().equals(faculty1.getName())).isTrue();
    }

    @Test
    void getFacultyByColorTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Faculty faculty1 = facultyController.createFaculty(new Faculty(0L, "math", "red"));
        Faculty faculty2 = facultyController.createFaculty(new Faculty(5L, "history", "white"));
        Faculty faculty3 = facultyController.createFaculty(new Faculty(6L, "music", "white"));


        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/color?color=red", String.class);
        List<Faculty> facultyList = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        assertThat(facultyList.size() == 1).isTrue();
        assertThat(facultyList.get(0).getColor().equals(faculty1.getColor())).isTrue();

    }

    @Test
    void getStudentsByFacultyTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Faculty faculty1 = facultyController.createFaculty(new Faculty(3L, "math", "red"));
        Faculty faculty2 = facultyController.createFaculty(new Faculty(5L, "history", "white"));
        Student student1 = new Student(1L, "roy", 22);
        Student student2 = new Student(5L, "ret", 42);
        student1.setFaculty(faculty1);
        student2.setFaculty(faculty1);
        Student studentDB1 = studentController.createStudent(student1);
        Student studentDB2 = studentController.createStudent(student2);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/" + faculty1.getId() + "/students", String.class);
        List<Student> students = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(students.size() == 2);
    }
}
