package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vol/organizations")
public class VolOrganController {
    private final VolOrganService volOrganService;
    private final VolActivityService volActivityService;

    // 기관 등록
    @PostMapping
    public DTOResponse<VolOrganIdDto> createOrgan(@RequestBody OrganizationForm organizationForm) {

        return new DTOResponse<>(volOrganService.createVolOrgan(organizationForm));
    }

    // 기관 찾기
    @GetMapping("/{id}")
    public DTOResponse<VolOrganDto> findOrgan(@PathVariable Long id) {

        return new DTOResponse<>(volOrganService.findOrgan(id));
    }

    // 기관 삭제
    @DeleteMapping("/{id}")
    public MessageResponse deleteOrgan(@PathVariable("id") Long organId) {
        volOrganService.deleteOrgan(organId);

        return MessageResponse.defaultOkayResponse();
    }

    // 기관 담당 활동 찾기
    @GetMapping("/{id}/activities")
    public DTOResponse<List<VolActivityDto>> findActivityByOrgan(@PathVariable("id") Long organId) {

        return new DTOResponse<>(volActivityService.findActivitiesByOrgan(organId));
    }
}
