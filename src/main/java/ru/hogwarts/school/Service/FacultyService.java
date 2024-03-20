package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.AlreadeCreatedException;
import ru.hogwarts.school.Exceptions.MyNotFoundException;
import ru.hogwarts.school.Model.Faculty;

import java.util.Map;

@Service
public class FacultyService {
    Map<Long, Faculty> facultyMap;
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
        if (!facultyMap.containsValue(id)) {
            throw new MyNotFoundException();
        }
        return facultyMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (!facultyMap.containsValue(faculty)) {
            throw new MyNotFoundException();
        }
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty removeFaculty(Long id) {
        if (!facultyMap.containsKey(id)) {
            throw new MyNotFoundException();
        }
        return facultyMap.remove(id);
    }

}
