package com.volunteer_platform.volunteer_platform.domain.volunteer.integration;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
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

import static org.assertj.core.api.Assertions.assertThat;

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
        ActivityForm activityForm = readValueFromJson();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/vol/activities")
                        .content(objectMapper.writeValueAsString(activityForm))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String answer = activityForm.getActivityName();
        VolActivityDto volActivityDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VolActivityDto.class);
        generatedActivityId = volActivityDto.getId();

        assertThat(volActivityDto.getActivityName()).isEqualTo(answer);
    }

    @Test
    @Order(2)
    void 생성된_봉사활동_조회() throws Exception {
        ActivityForm activityForm = readValueFromJson();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/vol/activities/" + generatedActivityId)).andReturn();
        VolActivityDto volActivityDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VolActivityDto.class);

        assertThat(generatedActivityId).isEqualTo(volActivityDto.getId());
        assertThat(volActivityDto.getActivityName()).isEqualTo(activityForm.getActivityName());
        assertThat(volActivityDto.getActivityContent()).isEqualTo(activityForm.getActivityContent());
        assertThat(volActivityDto.getActivityMethod()).isEqualTo(activityForm.getActivityMethod());
        assertThat(volActivityDto.getActivitySummary()).isEqualTo(activityForm.getActivitySummary());
    }

    private ActivityForm readValueFromJson() throws Exception {
        URL resource = ClassLoader.getSystemClassLoader().getResource("./json/volRequest.json");
        System.out.println("resource = " + resource);

        File file = new File(resource.getFile());
        return objectMapper.readValue(file, ActivityForm.class);
    }
}

