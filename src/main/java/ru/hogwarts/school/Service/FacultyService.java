package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.AlreadeCreatedException;
import ru.hogwarts.school.Exceptions.MyNotFoundException;
import ru.hogwarts.school.Model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        if (facultyMap.containsValue(faculty)) {
            throw new AlreadeCreatedException();
        }
        faculty.setId(++lastId);
        facultyMap.put(lastId, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        if (!facultyMap.containsKey(id)) {
            throw new MyNotFoundException();
        }
        return facultyMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty removeFaculty(Long id) {
        if (!facultyMap.containsKey(id)) {
            throw new MyNotFoundException();
        }
        return facultyMap.remove(id);
    }

    public List<Faculty> listColor(String color) {
        return facultyMap.values().stream()
                .filter(s -> s.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
