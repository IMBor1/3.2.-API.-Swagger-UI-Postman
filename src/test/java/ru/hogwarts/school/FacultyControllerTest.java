package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.Controllers.FacultyController;
import ru.hogwarts.school.Controllers.StudentController;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void createFacultyPostTest() throws Exception {
        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", new Faculty(1L, "math", "black"), Faculty.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
        Faculty newFaculty = newResponseEntity.getBody();

        ResponseEntity<Faculty> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/1", Faculty.class);

        Faculty faculty = responseEntity.getBody();
        assertThat(faculty.getId().equals(newFaculty.getId()));
        assertThat(faculty.getName().equals(newFaculty.getName()));
        assertThat(faculty.getColor().equals(newFaculty.getColor()));
    }

    @Test
    void findFacultyByIdGetTest() {
        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", new Faculty(2L, "music", "blue"), Faculty.class);
        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
        Faculty newFaculty = newResponseEntity.getBody();

        ResponseEntity<Faculty> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Faculty faculty = responseEntity.getBody();
        assertThat(faculty.getId().equals(newFaculty.getId()));
        assertThat(faculty.getName().equals(newFaculty.getName()));
        assertThat(faculty.getColor().equals(newFaculty.getColor()));
    }

    @Test
    void getFacultyByColorTest() {
        Faculty faculty1 = facultyController.createFaculty(new Faculty(0L, "math", "red"));
        Faculty faculty2 = facultyController.createFaculty(new Faculty(5L, "history", "white"));
        Faculty faculty3 = facultyController.createFaculty(new Faculty(6L, "music", "white"));
//        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
//                port + "/faculty", new Faculty(2L, "music", "blue"), Faculty.class);
//        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
//        Faculty newFaculty = newResponseEntity.getBody();

        ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/color" + faculty1.getColor(), ArrayList.class);

        ArrayList<Faculty> faculty = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertThat(faculty.contains(faculty1.getColor()));

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
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void findByNameIgnoreCaseOrColorIgnoreCaseGetTest() {
        Faculty faculty1 = facultyController.createFaculty(new Faculty(0L, "math", "red"));
        Faculty faculty2 = facultyController.createFaculty(new Faculty(5L, "history", "white"));
        ResponseEntity<Faculty> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/nameOrColor" + faculty1.getColor(), Faculty.class);
        Faculty faculty = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertThat(faculty.equals(faculty1));
    }

    @Test
    void getStudentsByFacultyTest() {
        Student student1 = studentController.createStudent(new Student(0L, "roy", 22));
        Student student2 = studentController.createStudent(new Student(5L, "ret", 42));
        Faculty faculty1 = facultyController.createFaculty(new Faculty(0L, "math", "red"));
        Faculty faculty2 = facultyController.createFaculty(new Faculty(5L, "history", "white"));
        student1.setFaculty(faculty1);
        student2.setFaculty(faculty1);
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/id/students" + faculty1.getStudents(), List.class);
        List<Faculty> faculty = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(faculty.contains(faculty1.getName()));
    }
}
