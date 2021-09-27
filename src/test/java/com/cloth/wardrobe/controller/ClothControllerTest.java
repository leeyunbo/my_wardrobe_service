package com.cloth.wardrobe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@NoArgsConstructor
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClothControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    public void beforeEach() {
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build();
    }

    @Test
    public void 옷_상세조회하기() throws Exception {
        //given
        final String url = "/api/v1/clothes/2";

        // when
        final ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders.get(url)
                .header("Authorization", "fdasrv34atdzb4zeex7y")
                .contentType(MediaType.APPLICATION_JSON) );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 옷리스트_조회하기() throws Exception {
        //given
        final String url = "/api/v1/clothes?page_number=1&page_size=10";
        // when
        final ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders.get(url)
                .header("Authorization", "fdasrv34atdzb4zeex7y")
                .contentType(MediaType.APPLICATION_JSON) );
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 코디리스트_조회하기() throws Exception {
        //given
        final String url = "/api/v1/clothes/1/records?page_number=1&page_size=10";

        // when
        final ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders.get(url)
                .header("Authorization", "fdasrv34atdzb4zeex7y")
                .contentType(MediaType.APPLICATION_JSON) );

        // then
        resultActions.andExpect(status().isOk());
    }

}
