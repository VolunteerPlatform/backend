package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationModifyForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VolOrganController {
    private final VolOrganService volOrganService;
    private final VolActivityService volActivityService;
    private final HttpServletRequest httpServletRequest;
    private final JwtTokenService jwtTokenService;

    // 기관 등록
    @PostMapping("/vol/organizations")
    public DTOResponse<VolOrganIdDto> createOrgan(@RequestBody OrganizationForm organizationForm) {
        return new DTOResponse<>(volOrganService.createVolOrgan(organizationForm, getMemberId()));
    }

    // 기관 찾기
    @GetMapping("/vol/organizations/{id}")
    public DTOResponse<VolOrganDto> findOrgan(@PathVariable Long id) {

        return new DTOResponse<>(volOrganService.findOrgan(id));
    }

    // 기관 삭제
    @DeleteMapping("/vol/organizations/{id}")
    public MessageResponse deleteOrgan(@PathVariable("id") Long organId) {
        volOrganService.deleteOrgan(organId);

        return MessageResponse.defaultOkayResponse();
    }

    // 기관 담당 활동 찾기
    @GetMapping("/vol/organizations/{id}/activities")
    public DTOResponse<List<VolActivityDto>> findActivityByOrgan(@PathVariable("id") Long organId) {

        return new DTOResponse<>(volActivityService.findActivitiesByOrgan(organId));
    }

    // 회원별 담당 조직 조회
    @GetMapping("/members/organizations")
    public DTOResponse<List<VolOrganDto>> findOrganByMember() {
        return new DTOResponse<>(volOrganService.findOrganByMember(getMemberId()));
    }

    @PutMapping("/vol/organizations/{id}")
    public MessageResponse modifyOrgan(@PathVariable("id") Long organId, @RequestBody OrganizationModifyForm modifyForm) {
        volOrganService.modifyOrgan(organId, modifyForm);

        return MessageResponse.defaultOkayResponse();
    }

    private Long getMemberId() {
        return jwtTokenService.tokenToUserId(httpServletRequest);
    }
}
