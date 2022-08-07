package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT COUNT (*) from student", nativeQuery = true)
    Integer quantityOfStudents();

    @Query(value = "SELECT AVG(age) from student", nativeQuery = true)
    Integer averageAgeOfStudents();

    @Query(value = "SELECT * from student order by id DESC LIMIT ?1", nativeQuery = true)
    List<Student> getLastStudents(int count);

    Collection<Student> findByAgeBetween(int min, int max);
}