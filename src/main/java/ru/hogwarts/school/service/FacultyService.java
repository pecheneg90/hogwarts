package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> sortAllFacultiesWithColor(String color) {
        return getAllFaculty().stream()
                .filter(faculties -> faculties.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String nameFaculty, String colorFaculty) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameFaculty, colorFaculty);
    }

    public Collection<Student> getStudentsToFaculty(Long id) {
        return facultyRepository.findById(id).map(Faculty::getStudents).orElse(null);
    }
}
