package com.volunteer_platform.volunteer_platform.service.vol;

import com.volunteer_platform.volunteer_platform.controller.form.Form;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivity;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivityTime;
import com.volunteer_platform.volunteer_platform.repository.vol.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.repository.vol.VolActivityTimeRepository;
import com.volunteer_platform.volunteer_platform.service.vol.volinterface.VolActivityTimeService;
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
