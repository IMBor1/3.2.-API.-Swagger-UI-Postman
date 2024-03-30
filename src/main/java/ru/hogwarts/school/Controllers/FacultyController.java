package ru.hogwarts.school.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/color")
    public List<Faculty> listFacultyByColor(@RequestParam String color) {
        return facultyService.listColor(color);
    }

    @GetMapping("{id}")
    public ResponseEntity findFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity editFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty faculty1 = facultyService.editFaculty(faculty);
        if (faculty1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nameOrColor")
    public ResponseEntity<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color) {
        if ((name != null && !name.isBlank()) && (color != null && !color.isBlank())) {
            return ResponseEntity.ok(facultyService.findByNameIgnoreCaseOrColorIgnoreCase(
                    name, color));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/students")
    public ResponseEntity getFacultyByStudent(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.findFaculty(id).getStudents());
    }
}
