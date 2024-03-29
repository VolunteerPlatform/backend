package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationModifyForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolOrganRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VolOrganServiceImpl implements VolOrganService {
    private final VolOrganRepository volOrganRepository;

    @Override
    public VolOrganIdDto createVolOrgan(OrganizationForm organizationForm, Long memberId) {
        VolOrgan volOrgan = VolOrgan.builder()
                .name(organizationForm.getName())
                .manager(organizationForm.getManager())
                .contact(organizationForm.getContact())
                .address(organizationForm.getAddress())
                .memberId(memberId)
                .build();

        volOrganRepository.save(volOrgan);
        return new VolOrganIdDto(volOrgan.getId());
    }

    @Override
    public void deleteOrgan(Long organId) {
        VolOrgan volOrgan = volOrganRepository.findById(organId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID 입니다."));

        if (!volOrgan.getVolActivities().isEmpty()) {
            throw new IllegalArgumentException("담당중인 활동이 있으면 기관 삭제가 불가능합니다.");
        }

        volOrganRepository.deleteById(organId);
    }

    @Override
    @Transactional
    public void modifyOrgan(Long organId, OrganizationModifyForm organizationModifyForm) {
        VolOrgan volOrgan = volOrganRepository.findById(organId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID 입니다."));

        volOrgan.modify(organizationModifyForm);
    }

    @Override
    @Transactional(readOnly = true)
    public VolOrganDto findOrgan(Long organId) {
        return VolOrganDto.of(volOrganRepository.findById(organId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID 입니다.")));
    }

    @Override
    public List<VolOrganDto> findOrganByMember(Long memberId) {
        return volOrganRepository.findVolOrganByMemberId(memberId)
                .stream()
                .map(VolOrganDto::of)
                .collect(Collectors.toList());
    }
}
