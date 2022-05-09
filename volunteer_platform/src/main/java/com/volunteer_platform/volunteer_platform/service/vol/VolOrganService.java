package com.volunteer_platform.volunteer_platform.service.vol;

import com.volunteer_platform.volunteer_platform.entity.register.VolOrgan;
import com.volunteer_platform.volunteer_platform.repository.vol.VolOrganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolOrganService {

    @PersistenceContext
    EntityManager em;
    private final VolOrganRepository volOrganRepository;

    @Transactional
    public void saveVolOrgan(VolOrgan volOrgan) {
        volOrganRepository.save(volOrgan);
    }
}
