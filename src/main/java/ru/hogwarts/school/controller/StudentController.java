package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        Student getStudent = studentService.getStudentById(studentId);
        if (getStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getStudent);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sort/{studentAge}")
    public ResponseEntity<Collection<Student>> sortAllStudentsWithAge(@PathVariable int studentAge) {
        return ResponseEntity.ok(studentService.sortAllStudentsWithAge(studentAge));
    }

    @GetMapping("/find/{min},{max}")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@PathVariable int min, @PathVariable int max) {
        Collection<Student> findByAgeBetween = studentService.findByAgeBetween(min, max);
        if (findByAgeBetween.isEmpty() || min < max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(findByAgeBetween);
    }

    @GetMapping("/amount")
    public Double getAmountOfStudents() {
        return studentService.quantityOfStudents();
    }

    @GetMapping("/average")
    public double getAverageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/last")
    public List<Student> lastStudents(int count) {
        return studentService.getLastStudents(count);
    }

    @GetMapping("/getAStudents")
    public List<String> getStudentsWithFirstALetter() {
        return studentService.getStudentsWithFirstLetterA();
    }

    @GetMapping("/averageStream")
    public double getAverageAgeOfStudentsStream(){
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/number")
    public int getNumber(){
        return studentService.getNumber();
    }
}