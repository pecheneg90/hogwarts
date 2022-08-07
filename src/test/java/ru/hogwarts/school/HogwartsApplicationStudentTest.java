package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsApplicationStudentTest {
	private Student student;

	private String baseUrl;

	private long studentId;

	@LocalServerPort
	private int localPort;

	@Autowired
	private StudentController studentController;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	void initTestData() {
		student = new Student();
		student.setId(1L);
		student.setName("studentName");
		student.setAge(20);
	}

	@BeforeEach
	public void setBaseUrl() {
		this.baseUrl = "http://localhost:" + localPort + "/student/";
	}

	@AfterEach
	void deleteTestData() {
		restTemplate.delete(baseUrl + studentId);
	}


	@Test
	void contextLoads() {
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	void createStudentTest() {
		Assertions
				.assertThat(this.restTemplate.postForObject(baseUrl, student, String.class))
				.isNotNull();
		Assertions
				.assertThat(this.restTemplate.postForObject(baseUrl, student, Student.class))
				.isEqualTo(student);
	}

	@Test
	void deleteStudentTest() {
		studentId = restTemplate.postForObject(baseUrl, student, Student.class).getId();
		student.setId(studentId);
		restTemplate.delete(baseUrl + studentId);
		Assertions
				.assertThat(this.restTemplate.getForObject(baseUrl + studentId, Student.class))
				.isNotIn(student);
	}

	@Test
	void getStudentByIdTest() {
		studentId = restTemplate.postForObject(baseUrl, student, Student.class).getId();
		Assertions
				.assertThat(this.restTemplate.getForObject(baseUrl + studentId, Student.class))
				.isEqualTo(student);
	}

	@Test
	void putStudentTest() {
		student.setAge(100);
		studentId = restTemplate.postForObject(baseUrl, student, Student.class).getId();
		student.setId(studentId);
		restTemplate.put(baseUrl, student);
		Assertions
				.assertThat(this.restTemplate.getForObject(baseUrl + studentId, Student.class))
				.isEqualTo(student);
	}

	@Test
	void getStudensByAgeTest() {
		student.setAge(9999);
		studentId = restTemplate.postForObject(baseUrl, student, Student.class).getId();
		student.setId(studentId);
		Assertions
				.assertThat(this.restTemplate.getForObject(baseUrl + "?age=" + 1000, Collection.class))
				.isNotNull();
	}

	@Test
	void getStudentsByRangeAgeTest() {
		student.setAge(1);
		studentId = restTemplate.postForObject(baseUrl, student, Student.class).getId();
		student.setAge(2);
		studentId = restTemplate.postForObject(baseUrl, student, Student.class).getId();
		student.setAge(3);
		studentId = restTemplate.postForObject(baseUrl, student, Student.class).getId();

		Assertions
				.assertThat(this.restTemplate.getForObject(baseUrl + "?ageMin=" + 80 + "&ageMax=" + 90, ArrayList.class).size())
				.isGreaterThan(3);
	}
}