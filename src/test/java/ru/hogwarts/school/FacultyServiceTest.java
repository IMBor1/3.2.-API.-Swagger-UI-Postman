package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Service.FacultyService;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @Mock
    FacultyRepository mockRepository;
    @InjectMocks
    FacultyService facultyService;

    Faculty faculty1 = new Faculty(1L, "history", "red");
    Faculty faculty2 = new Faculty(2L, "history", "pink");
    Faculty faculty3 = new Faculty(3L, "math", "red");
    List<Faculty> faculty = new ArrayList<>(List.of(faculty1, faculty2, faculty3));
    List<Faculty> facultyRed = new ArrayList<>(List.of(faculty1, faculty3));

    @Test
    void createFacultyByPostegree() {
        when(mockRepository.save(new Faculty(1L,
                "history", "red")))
                .thenReturn(faculty1);
        assertEquals(faculty1, facultyService.createFaculty(new Faculty(1L,
                "history", "red")));
    }

    @Test
    void findFacultyByPostegree() {
        when(mockRepository.findAll())
                .thenReturn(faculty);
        when(mockRepository.findById(1L).get())
                .thenReturn(faculty1);
        assertEquals(faculty1, facultyService.findFaculty(1L));
    }

    @Test
    void editFacultyByPostegree() {
//        mockRepository.save(new Faculty(1L,
//                "history", "red"));
        when(mockRepository.save(new Faculty(1L,
                "history", "pink")))
                .thenReturn(faculty2);
        assertEquals(faculty2, facultyService.editFaculty(new Faculty(1L,
                "history", "pink")));
    }

    //    @Test
//    void removeFaculty(){
//            mockRepository.save(new Faculty(1L,
//                "history", "red"));
//       // mockRepository.deleteById(1L);
//        facultyService.removeFaculty(1L);
//        assertEquals(0, mockRepository.);
    @Test
    void getColor() {
        when(mockRepository.findAll())
                .thenReturn(faculty);
        assertEquals(facultyRed, facultyService.listColor("red"));
    }
}
