package com.volunteer_platform.volunteer_platform.domain.volunteer.integration;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Address;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Coordinate;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Transactional
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VolActivityIntegrationTest {

    private final MockMvc mockMvc;
    private final EntityManager em;
    private final ObjectMapper objectMapper;
    private Long generatedActivityId;

    @Autowired
    public VolActivityIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, EntityManager entityManager) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.em = entityManager;

        // Response readValue 시에 발생하는 한글깨짐 문제 해결
        objectMapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
    }

    @Test
    @Order(1)
    void 봉사활동_생성() throws Exception {
        VolOrgan volOrgan = VolOrgan.builder()
                .manager("김안성")
                .name("안성시자원봉사센터")
                .address(Address.builder()
                        .detailAddress("안성시")
                        .coordinate(new Coordinate(127.0108, 37.0124))
                        .build())
                .build();

        em.persist(volOrgan);

        List<VolOrgan> organList = em.createQuery("select m from VolOrgan m", VolOrgan.class).getResultList();
        ActivityForm activityForm = ActivityForm.builder()
                .activityName("놀기5")
                .activityContent("아이들과 놀아주기")
                .activitySummary("아이들과 놀아주기")
                .activityBegin("2022-05-10")
                .activityEnd("2022-05-30")
                .timeList(new ArrayList<>())
                .recruitBegin("2022-04-10")
                .recruitEnd("2022-05-01")
                .activityMethod(ActivityMethod.OFFLINE)
                .organizationId(organList.get(0).getId())
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/vol/activities")
                        .content(objectMapper.writeValueAsString(activityForm))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        VolActivityIdDto volActivityIdDto = objectMapper.readValue(jsonObject.get("result").toString(), VolActivityIdDto.class);

        MvcResult getActivity = mockMvc.perform(MockMvcRequestBuilders.get("/vol/activities/" + volActivityIdDto.getActivityId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        jsonObject = new JSONObject(getActivity.getResponse().getContentAsString());
        VolActivityDto activityDto = objectMapper.readValue(jsonObject.get("result").toString(), VolActivityDto.class);

        generatedActivityId = volActivityIdDto.getActivityId();
        assertThat(activityDto.getActivityName()).isEqualTo(activityForm.getActivityName());
    }

    @Test
    @Order(2)
    void 생성된_봉사활동_조회() throws Exception {
        ActivityForm activityForm = ActivityForm.builder()
                .activityName("놀기5")
                .activityContent("아이들과 놀아주기")
                .activitySummary("아이들과 놀아주기")
                .activityBegin("2022-05-10")
                .activityEnd("2022-05-30")
                .activityMethod(ActivityMethod.OFFLINE)
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/vol/activities/" + generatedActivityId)).andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        VolActivityDto result = objectMapper.readValue(jsonObject.get("result").toString(), VolActivityDto.class);

        assertThat(generatedActivityId).isEqualTo(result.getId());
        assertThat(result.getActivityName()).isEqualTo(activityForm.getActivityName());
        assertThat(result.getActivityContent()).isEqualTo(activityForm.getActivityContent());
        assertThat(result.getActivityMethod()).isEqualTo(activityForm.getActivityMethod());
        assertThat(result.getActivitySummary()).isEqualTo(activityForm.getActivitySummary());
    }
}

