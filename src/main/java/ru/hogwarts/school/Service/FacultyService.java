package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.MyNotFoundException;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for find faculty by id");
        return facultyRepository.findById(id).orElseThrow(MyNotFoundException::new);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> listColor(String color) {
        logger.info("Was invoked method for listColor faculty");
        return facultyRepository.findAll().stream()
                .filter(s -> s.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for findByNameIgnoreCaseOrColorIgnoreCase faculty");
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public ResponseEntity<String> maxLengthFaculty() {
        String maxLength = facultyRepository.findAll().stream()

                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).orElseThrow();
        //.collect(Collectors.toList());
        return ResponseEntity.ok(maxLength);
    }
}
