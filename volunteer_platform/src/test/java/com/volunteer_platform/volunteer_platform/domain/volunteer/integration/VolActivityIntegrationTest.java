package com.volunteer_platform.volunteer_platform.domain.volunteer.integration;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VolActivityIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private Long generatedActivityId;

    @Autowired
    public VolActivityIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;

        // Response readValue 시에 발생하는 한글깨짐 문제 해결
        objectMapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
    }

    @Test
    @Order(1)
    void 봉사활동_생성() throws Exception {
        Form form = readValueFromJson();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/vol/act")
                        .content(objectMapper.writeValueAsString(form))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String answer = form.getVolActivityForm().getActivityName();
        VolActivityDto volActivityDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VolActivityDto.class);
        generatedActivityId = volActivityDto.getId();

        Assertions.assertThat(volActivityDto.getActivityName()).isEqualTo(answer);
    }

    @Test
    @Order(2)
    void 생성된_봉사활동_조회() throws Exception {
        Form form = readValueFromJson();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/vol/act/" + generatedActivityId)).andReturn();
        VolActivityDto volActivityDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VolActivityDto.class);

        Assertions.assertThat(generatedActivityId).isEqualTo(volActivityDto.getId());
    }

    private Form readValueFromJson() throws Exception {
        URL resource = ClassLoader.getSystemClassLoader().getResource("./json/volRequest.json");
        System.out.println("resource = " + resource);

        File file = new File(resource.getFile());
        Form form = objectMapper.readValue(file, Form.class);
        return form;
    }
}

