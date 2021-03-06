package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolOrganForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolOrganRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolOrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolOrganServiceImpl implements VolOrganService {

    @PersistenceContext
    EntityManager em;
    private final VolOrganRepository volOrganRepository;

    @Transactional
    public void saveVolOrgan(VolOrgan volOrgan) {
        volOrganRepository.save(volOrgan);
    }

    @Override
    @Transactional
    public VolOrgan createVolOrgan(VolOrganForm form) {
        if (form.getId() != null) {
            VolOrgan findOrgan = volOrganRepository.findById(form.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID입니다. 기관을 생성해주세요."));

            return findOrgan;
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
    @Transactional
    public void deleteOrgan(Long organId) {
        VolOrgan volOrgan = volOrganRepository.findById(organId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID 입니다."));

        if (volOrgan.getVolActivities().size() != 0) {
            throw new IllegalArgumentException("담당중인 활동이 있으면 기관 삭제가 불가능합니다.");
        }

        volOrganRepository.deleteById(organId);
    }

}
