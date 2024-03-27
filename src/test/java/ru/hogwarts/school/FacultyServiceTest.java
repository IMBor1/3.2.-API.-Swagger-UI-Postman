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
    private final FacultyService facultyService;

    public FacultyServiceTest(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    Faculty faculty1 = new Faculty(1L, "history", "red");
    Faculty faculty2 = new Faculty(1L, "history", "red");
    List<Faculty> faculty = new ArrayList<>(List.of(faculty1, faculty2));

    @Test
    void createFacultyByPostegree() {
        when(mockRepository.findAll())
                .thenReturn(faculty);
        assertEquals(faculty1, faculty.get(1));
    }

}
