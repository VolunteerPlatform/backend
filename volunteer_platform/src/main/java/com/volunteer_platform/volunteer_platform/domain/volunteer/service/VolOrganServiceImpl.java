package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
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
    public VolOrgan createVolOrgan(Form form) {
        VolOrgan volOrgan = VolOrgan.builder()
                .name(form.getVolOrganForm().getName())
                .manager(form.getVolOrganForm().getManager())
                .organPhoneNumber(form.getVolOrganForm().getOrganPhoneNumber())
                .address(form.getVolOrganForm().getAddress())
                .build();

        saveVolOrgan(volOrgan);
        return volOrgan;
    }
}
