package com.reward.services.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // =========================
    // API 1 Tests
    // =========================

    @Test
    void shouldGetAllRewards() throws Exception {

        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk());
    }

    // =========================
    // API 2 Tests
    // =========================

    @Test
    void shouldGetRewardsByCustomerId() throws Exception {

        mockMvc.perform(get("/api/rewards/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId")
                        .value(101));
    }

    @Test
    void shouldReturnErrorForInvalidCustomer()
            throws Exception {

        mockMvc.perform(get("/api/rewards/999"))
                .andExpect(status().is5xxServerError());
    }

    // =========================
    // API 3 Tests
    // =========================

    @Test
    void shouldGetAllCustomers() throws Exception {

        mockMvc.perform(get("/api/rewards/customers"))
                .andExpect(status().isOk());
    }
}