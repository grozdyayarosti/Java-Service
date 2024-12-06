package com.example.toolsleasing;

import com.example.toolsleasing.controllers.CControllerFruits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CControllerTest {

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
    void get201() throws Exception {
        mockMvc.perform(get("/fruit_store/get201")).andExpect(status().isCreated());
    }

    @Test
    void getJSON() throws Exception {
        mockMvc.perform(get("/fruit_store/getJSON"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Яблоко"))
                .andExpect(jsonPath("$.country").value("Россия"))
                .andExpect(jsonPath("$.price").value(150));
    }
}
