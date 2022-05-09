package com.volunteer_platform.volunteer_platform.service.vol;

import com.volunteer_platform.volunteer_platform.entity.register.VolActivityTime;
import com.volunteer_platform.volunteer_platform.repository.vol.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.repository.vol.VolActivityTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolActivityTimeService {

    @PersistenceContext
    EntityManager em;
    private final VolActivityTimeRepository volActivityTimeRepository;

    @Transactional
    public void saveVolActivityTime(VolActivityTime volActivityTime) {
        volActivityTimeRepository.save(volActivityTime);
    }
}
