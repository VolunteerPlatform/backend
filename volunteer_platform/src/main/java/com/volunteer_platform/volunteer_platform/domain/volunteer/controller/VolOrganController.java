package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivitySummaryDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolOrganForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolOrganRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vol/organ")
public class VolOrganController {
    private final VolOrganService volOrganService;
    private final VolActivityService volActivityService;
    private final VolOrganRepository volOrganRepository;

    // 기관 등록
    @PostMapping()
    public VolOrganDto createOrgan(@RequestBody VolOrganForm form) {
        VolOrgan volOrgan = volOrganService.createVolOrgan(form);

        return VolOrganDto.of(volOrgan);
    }

    // 기관 찾기
    @GetMapping("/{id}")
    public VolOrganDto findOrgan(@PathVariable Long id) {
        VolOrgan volOrgan = volOrganRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID 입니다."));

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
    public List<VolActivitySummaryDto> findActivityByOrgan(@PathVariable("id") Long organId) {
        return volActivityService.findActivitiesByOrgan(organId).stream()
                .map(VolActivitySummaryDto::of).collect(Collectors.toList());
    }
}
