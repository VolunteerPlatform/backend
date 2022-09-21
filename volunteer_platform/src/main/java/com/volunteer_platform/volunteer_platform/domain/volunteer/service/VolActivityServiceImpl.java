package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.timetable.repository.TimeTableRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.SearchCondition;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivitySessionDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityTimeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityDayOfWeek;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.CustomSearchRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolOrganRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolActivityServiceImpl implements VolActivityService {

    private final VolActivityRepository volActivityRepository;
    private final VolActivitySessionRepository volActivitySessionRepository;

    private final CustomSearchRepository customSearchRepository;

    private final TimeTableRepository timeTableRepository;

    private final VolOrganRepository volOrganRepository;

    @Override
    @Transactional
    public VolActivityIdDto createVolActivity(ActivityForm activityForm) {
        VolOrgan volOrgan = findOrgan(activityForm.getOrganizationId());
        VolActivity volActivity = activityForm.toEntity(volOrgan);

        createDayOfWeek(activityForm.getTimeList(), volActivity);
        volActivityRepository.save(volActivity);

        createSessions(activityForm.getTimeList(), volActivity);

        return new VolActivityIdDto(volActivity.getId());
    }

    @Override
    public List<VolActivityDto> findActivitiesByOrgan(Long organId) {
        return volActivityRepository.findByVolOrgan(organId)
                .stream()
                .map(VolActivityDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchResultDto> searchActivity(SearchCondition searchCondition) {
        isCoordinatePresent(searchCondition);

//        return filterByTimeTable(customSearchRepository.searchActivity(searchCondition));
        return customSearchRepository.searchActivity(searchCondition);
    }

    @Override
    @Transactional
    public void deleteActivity(Long activityId) {
        VolActivity volActivity = volActivityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 활동 ID 입니다."));

        volActivity.delete();

        List<VolActivitySession> activitySessionList = volActivitySessionRepository.findAllByVolActivityId(activityId);
        activitySessionList
                .stream()
                .filter(session -> session.getSessionStatus() != SessionStatus.COMPLETE)
                .forEach(session -> {
                    session.changeStatus(SessionStatus.DELETED);
                    if (session.getNumOfApplicant() > 0) {
                        session.getAppHistories()
                                .forEach(application -> {
                                    application.setIsAuthorized(IsAuthorized.DISAPPROVAL);
                                    volActivitySessionRepository.decreaseNumOfApplicant(session.getId());
                                });
                    }
                });
    }

    @Override
    public VolActivityDto findActivityById(Long activityId) {
        VolActivity volActivity = volActivityRepository.findByIdWithOrgan(activityId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 활동 ID 입니다."));

        return VolActivityDto.of(volActivity);
    }

    @Override
    public List<VolActivitySessionDto> findSessionsOfActivity(Long activityId) {
        return volActivitySessionRepository.findAllByVolActivityId(activityId)
                .stream()
                .map(VolActivitySessionDto::of)
                .collect(Collectors.toList());
    }

    private VolOrgan findOrgan(Long organizationId) {
        return volOrganRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기관 ID 입니다."));
    }

    // 활동의 요일과 시작, 종료시간 별도 저장
    private void createDayOfWeek(List<ActivityTimeForm> activityTimeForms, VolActivity volActivity) {
        activityTimeForms
                .forEach(element -> volActivity.getDayOfWeeks().add(element.toEntity(volActivity)));

        volActivity.getDayOfWeeks().sort(Comparator.comparing(VolActivityDayOfWeek::getActivityWeek));
    }

    // 봉사활동 세션 만들기
    private void createSessions(List<ActivityTimeForm> activityTimeForms, VolActivity volActivity) {
        Map<DayOfWeek, List<ActivityTimeForm>> mapOfActivityTimes = getMapOfActivityTimes(activityTimeForms);

        List<VolActivitySession> activitySessions = new ArrayList<>();
        LocalDate startDate = volActivity.getActivityPeriod().getBegin();
        LocalDate oneDayAfterEndDate = volActivity.getActivityPeriod().getEnd().plusDays(1);

        startDate.datesUntil(oneDayAfterEndDate).forEach(
                date -> {
                    if (mapOfActivityTimes.get(date.getDayOfWeek()) != null) {
                        for (ActivityTimeForm timeForm : mapOfActivityTimes.get(date.getDayOfWeek())) {
                            VolActivitySession activitySession = VolActivitySession.builder()
                                    .volActivity(volActivity)
                                    .activityWeek(date.getDayOfWeek())
                                    .startTime(timeForm.getStartTime())
                                    .endTime(timeForm.getEndTime())
                                    .activityDate(date)
                                    .numOfRecruit(timeForm.getNumOfRecruit())
                                    .numOfApplicant(0)
                                    .sessionStatus(SessionStatus.RECRUITING)
                                    .build();

                            activitySessions.add(activitySession);
                        }
                    }
                });

        volActivitySessionRepository.saveAll(activitySessions);
    }

    // 세션 제작을 위한 각 요일별 활동 시간들을 Map 형태로 변환
    private Map<DayOfWeek, List<ActivityTimeForm>> getMapOfActivityTimes(List<ActivityTimeForm> activityTimeForms) {
        return activityTimeForms.stream()
                .collect(Collectors.groupingBy(ActivityTimeForm::getActivityWeek));
    }

    private List<SearchResultDto> filterByTimeTable(List<SearchResultDto> searchResultList) {
        Map<DayOfWeek, List<TimeTable>> timeTableOfMember = getTimeTableOfMember();
        return searchResultList.stream()
                .filter(session -> isAttendable(timeTableOfMember, session))
                .collect(Collectors.toList());
    }

    private Map<DayOfWeek, List<TimeTable>> getTimeTableOfMember() {
        Member currentMember = ((Member) SecurityContextHolder.getContext().getAuthentication().getDetails());
        if (currentMember == null) {
            throw new IllegalStateException("로그인 정보가 없는 사용자입니다.");
        }

        List<TimeTable> timeTables = timeTableRepository.findTimetableByMemberId(currentMember.getId());

        return timeTables.stream()
                .collect(Collectors.groupingBy(TimeTable::getDayOfWeek, Collectors.toList()));
    }

    private boolean isAttendable(Map<DayOfWeek, List<TimeTable>> timeTables, SearchResultDto searchResultDto) {
        DayOfWeek dayOfWeek = searchResultDto.getActivityDate().getDayOfWeek();
        List<TimeTable> attendableTimeList = timeTables.get(dayOfWeek);

        for (TimeTable timeTable : attendableTimeList) {
            if (timeTable.getStartTime() <= searchResultDto.getStartTime()
                    && timeTable.getEndTime() >= searchResultDto.getEndTime()) {
                return true;
            }
        }

        return false;
    }

    private void isCoordinatePresent(SearchCondition searchCondition) {
        if (searchCondition.getLongitude() == null || searchCondition.getLatitude() == null) {
            throw new IllegalArgumentException("봉사활동 검색시 위도와 경도값은 필수입니다.");
        }
    }
}
