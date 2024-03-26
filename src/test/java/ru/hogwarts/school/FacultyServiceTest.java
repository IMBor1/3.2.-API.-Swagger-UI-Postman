package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Service.FacultyService;
import ru.hogwarts.school.repository.FacultyRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @Mock
    FacultyRepository mockRepository;
    @InjectMocks
    private final FacultyService facultyService;

    public FacultyServiceTest(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    Faculty faculty1 = new Faculty(1, "history", "red");

    @Test
    void createFacultyByPostegree() {
        Faculty result = facultyService.createFaculty(new Faculty(1,
                "history", "red"));
        assertEquals(faculty1, result);
    }

}
