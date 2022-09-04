package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolOrganRepository;
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
    private final VolOrganRepository volOrganRepository;

    // 기관 등록
    @PostMapping
    public VolOrganDto createOrgan(@RequestBody OrganizationForm organizationForm) {
        VolOrgan volOrgan = volOrganService.createVolOrgan(organizationForm);

        return VolOrganDto.of(volOrgan);
    }

    // 기관 찾기
    @GetMapping("/{id}")
    public VolOrganDto findOrgan(@PathVariable Long id) {
        VolOrgan volOrgan = volOrganService.findOrgan(id);

        return VolOrganDto.of(volOrgan);
    }

    // 기관 삭제
    @DeleteMapping("/{id}")
    public String deleteOrgan(@PathVariable("id") Long organId) {
        volOrganService.deleteOrgan(organId);

        return "redirect:/";
    }

    // 기관 담당 활동 찾기
    @GetMapping("/{id}/activities")
    public List<VolActivityDto> findActivityByOrgan(@PathVariable("id") Long organId) {
        return volActivityService.findActivitiesByOrgan(organId);
    }
}
