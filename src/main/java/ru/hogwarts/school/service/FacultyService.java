package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty) {
        logger.info("method has been called - createFaculty:{}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        logger.info("method has been called - getFacultyById:{}", facultyId);
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("method has been called - updateFaculty:{}", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        logger.info("method has been called - deleteFaculty:{}", facultyId);
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("method has been called - getAllFaculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> sortAllFacultiesWithColor(String color) {
        logger.info("method has been called - sortAllFacultiesWithColor");
        return getAllFaculty().stream()
                .filter(faculties -> faculties.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String nameFaculty, String colorFaculty) {
        logger.info("method has been called - findByNameIgnoreCaseOrColorIgnoreCase");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameFaculty, colorFaculty);
    }

    public Collection<Student> findStudentsByFaculty(Long facultyId) {
        logger.info("method has been called - findStudentsByFaculty");
        return facultyRepository.findById(facultyId).map(Faculty::getStudents).orElse(null);
    }

    public String getLongestFacultyName() throws Exception {
        logger.info("method has been called - getLongestFacultyName");
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElseThrow(() -> new Exception("Not found"));
    }
}