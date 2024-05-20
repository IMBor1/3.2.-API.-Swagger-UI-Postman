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
    // Инжектим репозиторий
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //Создаем факультет
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    //Поиск факультета
    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for find faculty by id");
        return facultyRepository.findById(id).orElseThrow(MyNotFoundException::new);
    }

    //изменить факультет
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    //удалить факультет
    public void removeFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    //список факультетов с одним цветом
    public List<Faculty> listColor(String color) {
        logger.info("Was invoked method for listColor faculty");
        return facultyRepository.findAll().stream()
                .filter(s -> s.getColor().equals(color))
                .collect(Collectors.toList());
    }

    //поиск игнорируя регистр
    public List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for findByNameIgnoreCaseOrColorIgnoreCase faculty");
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    //максимальная длина факультета
    public ResponseEntity<String> maxLengthFaculty() {
        String maxLength = facultyRepository.findAll().stream()

                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).orElseThrow();
        return ResponseEntity.ok(maxLength);
    }
}
