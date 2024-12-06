package com.example.toolsleasing;

import com.example.toolsleasing.controllers.CControllerFruits;
import com.example.toolsleasing.repositories.IRepositoryFruits;
import com.example.toolsleasing.services.CServiceReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CControllerTest {

    @Mock
    private IRepositoryFruits repositoryFruits;
    @Mock
    private CServiceReport serviceReport;

    @InjectMocks
    private CControllerFruits cControllerFruits;

    private MockMvc mockMvc;

    public CControllerTest() {
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cControllerFruits).build();
    }

    @Test
    void get200() throws Exception {
        mockMvc.perform(get("/fruit_store/get200")).andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/fruit_store/all_products")).andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {

//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode json = mapper.createObjectNode()
//                .put("name", "Джон Доу")
//                .put("age", 30);
//        String name = repositoryFruits.findById(id).value.name
        mockMvc.perform(get("/fruit_store/beb"))
                .andExpect(jsonPath("$.name").value("Яблоко"));
    }

    @Test
    void getReport() throws Exception {
        mockMvc.perform(get("/fruit_store/report/{report_number}", 1))
                .andExpect(status().isCreated());
    }
}
