SELECT student.name, student.age, faculty.name as faculty_name
FROM student
         INNER JOIN faculty ON student.faculty_id = faculty.faculty_id;

SELECT student.name, student.age
FROM student
         LEFT JOIN avatar ON student.id = avatar.student_id;