select * from student;

select *
from student
where age > 10
  AND age < 20;

select name from student;

select *
from student
where name LIKE '%e%'
and name LIKE  '%s%';

select *
from student
where id > student.age;

select *
from student
order by age;

