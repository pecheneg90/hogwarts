package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        logger.info("method has been called - createStudent:{}", student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        logger.info("method has been called - updateStudent:{}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        logger.info("method has been called - deleteStudent:{}", studentId);
        studentRepository.deleteById(studentId);
    }

    public Student getStudentById(Long studentId) {
        logger.info("method has been called - getStudentById:{}", studentId);
        return studentRepository.findById(studentId).get();
    }

    public Collection<Student> getAllStudents() {
        logger.info("method has been called - getAllStudents");
        return studentRepository.findAll();
    }

    public Collection<Student> sortAllStudentsWithAge(int age) {
        logger.info("method has been called - sortAllStudentsWithAge");
        return getAllStudents().stream()
                .filter(students -> students.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("method has been called - findByAgeBetween");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Double quantityOfStudents() {
        logger.info("method has been called - quantityOfStudents");
        return studentRepository.quantityOfStudents();
    }

    public Double averageAgeOfStudents() {
        logger.info("method has been called - averageAgeOfStudents");
        return studentRepository.averageAgeOfStudents();
    }

    public List<Student> getLastStudents(int count) {
        logger.info("method has been called - getLastStudents");
        return studentRepository.getLastStudents(count);
    }

    public List<String> getStudentsWithFirstLetterA() {
        logger.info("method has been called - first letter A");
        List<Student> students = studentRepository.findAll();

        return students.stream().
                parallel().
                map(Student::getName).
                filter(e -> e.startsWith("A")).
                sorted().
                collect(Collectors.toList());
    }

    public double getAverageAgeOfStudents() {
        logger.info("method has been called - getAverageAgeOfStudents");
        List<Student> students = studentRepository.findAll();
        return students.stream().
                mapToDouble(Student::getAge).
                average().orElse(0.0);
    }

    public int getNumber() {
        logger.info("method has been called - getNumber");
        long start = System.currentTimeMillis();
        int sum = Stream.
                iterate(1, a -> a + 1).
                limit(1_000_000).
                parallel().
                reduce(0, Integer::sum);
        System.out.println(System.currentTimeMillis() - start);
        return sum;
    }
}