package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.Controllers.FacultyController;
import ru.hogwarts.school.Model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;

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
//    @Test
//    void getFacultyBeColorTest() {
//
//        ResponseEntity<Faculty> newResponseEntity = restTemplate.postForEntity("http://localhost:" +
//                port + "/faculty", new Faculty(2L, "music", "blue"), Faculty.class);
//        assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK));
//        Faculty newFaculty = newResponseEntity.getBody();
//
//        ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity("http://localhost:"
//                + port + "/faculty/color" + newFaculty.getColor(), ArrayList.class);
//
//        ArrayList<Faculty> faculty = responseEntity.getBody();
//        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
//        assertThat(faculty.get(0).getColor().equals(newFaculty.getColor()));

    //    }
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

}
