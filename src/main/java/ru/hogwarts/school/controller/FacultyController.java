package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RequestMapping("faculty")
@RestController
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createFaculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long facultyId) {
        Faculty getFaculty = facultyService.getFacultyById(facultyId);
        if (getFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getFaculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @PutMapping()
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sort/{facultyColor}")
    public ResponseEntity<Collection<Faculty>> sortAllFacultiesWithColor(@PathVariable String facultyColor) {
        return ResponseEntity.ok(facultyService.sortAllFacultiesWithColor(facultyColor));
    }

    @GetMapping("/find/{nameFaculty}, {colorFaculty}")
    public ResponseEntity<Collection<Faculty>> findByNameIgnoreCaseOrColorIgnoreCase(@PathVariable String nameFaculty, @PathVariable String colorFaculty) {
        Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase =
                facultyService.findByNameIgnoreCaseOrColorIgnoreCase(nameFaculty, colorFaculty);
        if (findByNameIgnoreCaseOrColorIgnoreCase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(findByNameIgnoreCaseOrColorIgnoreCase);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Collection<Student>> getStudents(@PathVariable Long id) {
        Collection<Student> students = facultyService.getStudentsToFaculty(id);
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }
}