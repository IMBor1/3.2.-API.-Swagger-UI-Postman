package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.Model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
