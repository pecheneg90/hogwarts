package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    private int count = 0;
    private final Object flag = new Object();

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
        logger.debug("time - {}", start);
        return sum;
    }

    public ResponseEntity<Collection<String>> getStudentNameNotSync() {
        logger.info("method has been called - getStudentNameNotSync");
        List<String> students = getStudentNames(studentRepository);

        for (int i = 0; i < 2; i++) {
            System.out.printf("%s%d.%s%n", "Main thread - ", i, students.get(i));
        }

        new Thread(() -> {
            for (int i = 2; i < 4; i++) {
                System.out.printf("%s%d.%s%n", "First thread - ", i, students.get(i));
            }
        }).start();

        new Thread(() -> {
            for (int i = 5; i < 7; i++) {
                System.out.printf("%s%d.%s%n", "Second thread - ", i, students.get(i));
            }
        }).start();
        return ResponseEntity.ok(students);
    }

    public ResponseEntity<Collection<String>> getStudentNameSync() {
        logger.info("method has been called - getStudentNameSync()");
        List<String> students = getStudentNames(studentRepository);

        printNamesOfListSync(students);
        printNamesOfListSync(students);

        new Thread(() -> {
            printNamesOfListSync(students);
            printNamesOfListSync(students);

        }).start();

        new Thread(() -> {
            printNamesOfListSync(students);
            printNamesOfListSync(students);

        }).start();

        return ResponseEntity.ok(students);
    }

    public List<String> getStudentNames(StudentRepository studentRepository) {
        logger.info("method has been called - getStudentName");
        return studentRepository
                .findAll()
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public void printNamesOfListSync(List<String> students) {
        logger.info("method has been called - synchronized");
        synchronized (flag) {
            System.out.printf("%d.%s%n",count,students.get(count));
            count++;
        }
    }
}