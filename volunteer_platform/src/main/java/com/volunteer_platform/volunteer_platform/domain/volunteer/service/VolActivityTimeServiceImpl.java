package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityTimeRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolActivityTimeServiceImpl implements VolActivityTimeService {

    @PersistenceContext
    EntityManager em;
    private final VolActivityTimeRepository volActivityTimeRepository;

    @Transactional
    public void saveVolActivityTime(VolActivityTime volActivityTime) {
        volActivityTimeRepository.save(volActivityTime);
    }

    @Override
    @Transactional
    public void createVolActivityTime(Form form, VolActivity volActivity) {
        for (int i = 0; i < form.getVolActivityTimeForm().getActivityWeek().size(); i++) {
            VolActivityTime volActivityTime = VolActivityTime.builder()
                    .volActivity(volActivity)
                    .activityWeek(form.getVolActivityTimeForm().getActivityWeek().get(i))
                    .startTime(form.getVolActivityTimeForm().getStartTime())
                    .endTime(form.getVolActivityTimeForm().getEndTime())
                    .build();

            saveVolActivityTime(volActivityTime);
        }
    }
}
