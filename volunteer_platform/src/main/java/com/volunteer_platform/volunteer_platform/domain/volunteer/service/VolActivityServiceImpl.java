package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.SearchCondition;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityTimeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityDayOfWeek;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.CustomSearchRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.volunteer_platform.volunteer_platform.domain.timetable.models.QTimeTable.timeTable;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolActivityServiceImpl implements VolActivityService {

    private final VolActivityRepository volActivityRepository;
    private final VolActivitySessionRepository volActivitySessionRepository;

    private final CustomSearchRepository customSearchRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public VolActivityDto createVolActivity(ActivityForm activityForm, VolOrgan volOrgan) {
        VolActivity volActivity = activityForm.toEntity(volOrgan);

        createDayOfWeek(activityForm.getTimeList(), volActivity);
        volActivityRepository.save(volActivity);

        createSessions(activityForm.getTimeList(), volActivity);

        return VolActivityDto.of(volActivity);
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
        List<SearchResultDto> searchResult = customSearchRepository.searchActivity(searchCondition);

//        return filterByTimeTable(searchResult);
        return searchResult;
    }

    @Override
    public VolActivityDto findActivityById(Long activityId) {
        VolActivity volActivity = volActivityRepository.findByIdWithOrgan(activityId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 활동 ID 입니다."));

        return VolActivityDto.of(volActivity);
    }

    // 활동의 요일과 시작, 종료시간 별도 저장
    private void createDayOfWeek(List<ActivityTimeForm> activityTimeForms, VolActivity volActivity) {
        activityTimeForms
                .forEach(element -> volActivity.getDayOfWeeks().add(element.toEntity(volActivity)));

        volActivity.getDayOfWeeks().sort(Comparator.comparing(VolActivityDayOfWeek::getActivityWeek));
    }

    // 봉사활동 세션 만들기
    private void createSessions(List<ActivityTimeForm> activityTimeForms, VolActivity volActivity) {
        EnumMap<DayOfWeek, List<ActivityTimeForm>> mapOfActivityTimes = getMapOfActivityTimes(activityTimeForms);

        List<VolActivitySession> activitySessions = new ArrayList<>();
        LocalDate startDate = volActivity.getActivityPeriod().getBegin();
        LocalDate oneDayAfterEndDate = volActivity.getActivityPeriod().getEnd().plusDays(1);

        startDate.datesUntil(oneDayAfterEndDate).forEach(
                date -> {
                        for (ActivityTimeForm timeForm : mapOfActivityTimes.get(date.getDayOfWeek())) {
                            VolActivitySession activitySession = VolActivitySession.builder()
                                    .volActivity(volActivity)
                                    .activityWeek(date.getDayOfWeek())
                                    .startTime(timeForm.getStartTime())
                                    .endTime(timeForm.getEndTime())
                                    .activityDate(date)
                                    .numOfRecruit(volActivity.getNumOfRecruit())
                                    .numOfApplicant(0)
                                    .sessionStatus(SessionStatus.RECRUITING)
                                    .build();

                            activitySessions.add(activitySession);
                        }
                });

        volActivitySessionRepository.saveAll(activitySessions);
    }

    // 세션 제작을 위한 각 요일별 활동 시간들을 Map 형태로 변환
    private EnumMap<DayOfWeek, List<ActivityTimeForm>> getMapOfActivityTimes(List<ActivityTimeForm> activityTimeForms) {
        EnumMap<DayOfWeek, List<ActivityTimeForm>> dayOfWeekMap = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek value : DayOfWeek.values()) {
            dayOfWeekMap.put(value, new ArrayList<>());
        }

        for (ActivityTimeForm timeForm : activityTimeForms) {
            dayOfWeekMap.get(timeForm.getActivityWeek())
                    .add(ActivityTimeForm
                            .builder()
                            .activityWeek(timeForm.getActivityWeek())
                            .startTime(timeForm.getStartTime())
                            .endTime(timeForm.getEndTime())
                            .build());
        }

        return dayOfWeekMap;
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

        List<TimeTable> timeTables = queryFactory
                .selectFrom(timeTable)
                .where(timeTable.member.id.eq(currentMember.getId()))
                .fetch();

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
