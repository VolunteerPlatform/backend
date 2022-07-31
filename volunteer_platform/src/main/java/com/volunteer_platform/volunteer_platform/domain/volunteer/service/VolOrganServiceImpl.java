package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolOrganForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolOrganRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VolOrganServiceImpl implements VolOrganService {
    private final VolOrganRepository volOrganRepository;

    public void saveVolOrgan(VolOrgan volOrgan) {
        volOrganRepository.save(volOrgan);
    }

    @Override
    public VolOrgan createVolOrgan(VolOrganForm form) {
        if (form.getId() != null) {
            return findOrgan(form.getId());
        }

        VolOrgan volOrgan = VolOrgan.builder()
                .name(form.getName())
                .manager(form.getManager())
                .organPhoneNumber(form.getOrganPhoneNumber())
                .address(form.getAddress())
                .build();

        saveVolOrgan(volOrgan);
        return volOrgan;
    }

    @Override
    public void deleteOrgan(Long organId) {
        VolOrgan volOrgan = findOrgan(organId);

        if (!volOrgan.getVolActivities().isEmpty()) {
            throw new IllegalArgumentException("담당중인 활동이 있으면 기관 삭제가 불가능합니다.");
        }

        volOrganRepository.deleteById(organId);
    }

    private VolOrgan findOrgan(Long organId) {
        return volOrganRepository.findById(organId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID 입니다."));
    }


}
