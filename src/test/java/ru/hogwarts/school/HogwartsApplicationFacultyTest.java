package ru.hogwarts.school;

import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class HogwartsApplicationFacultyTest {

    private Faculty faculty;

    private List<Faculty> facultyList;

    private JSONObject facultyObject;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    void initDataForTest() throws JSONException {
        faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("Test Faculty1");
        faculty.setColor("white");

        Faculty faculty1 = new Faculty();
        faculty1.setFacultyId(2L);
        faculty1.setName("Test Faculty2");
        faculty1.setColor("red");

        facultyList = List.of(
                faculty,
                faculty1
        );


        facultyObject = new JSONObject();
        facultyObject.put("name", "Test Faculty1");
        facultyObject.put("color", "white");


    }

    @Test
    void createFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Faculty1"))
                .andExpect(jsonPath("$.color").value("white"));


    }

    @Test
    void getFacultyByIdTest() throws Exception {
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Faculty1"))
                .andExpect(jsonPath("$.color").value("white"));

    }

    @Test
    void updateFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Faculty1"))
                .andExpect(jsonPath("$.color").value("white"));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        doNothing().when(facultyRepository).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + 1L))
                .andExpect(status().isOk());
    }

    @Test
    void findFacultyByColor() throws Exception {
        when(facultyRepository.findByColorIgnoreCase("white")).thenReturn((List<Faculty>) facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/?color=" + "white")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void findFacultyByNameAndColor() throws Exception {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString())).thenReturn((List<Faculty>) facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/params/?color=" + "white" + "&name=" + "Test Faculty1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].color").isNotEmpty());
    }
}