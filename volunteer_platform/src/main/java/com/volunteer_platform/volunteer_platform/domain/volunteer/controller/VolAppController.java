package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.AppHistoryDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.ApplicantDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.AuthorizeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolAppForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.VolAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VolAppController {

    private final VolAppService volAppService;

    // 봉사 신청 POST API
    @PostMapping("/vol/sessions/{activityTimeId}")
    public AppHistoryDto volApply(@PathVariable Long activityTimeId, @RequestBody VolAppForm volAppForm) {
        AppHistory appHistory = volAppService.volApply(activityTimeId, volAppForm);

        return AppHistoryDto.of(appHistory);
    }

    // 신청 봉사자 정보 GET API -> 쿼리 파라미터로 상태(PEND/ACCEPTED/DENY/FINISH 인지)
    @GetMapping(path = "/vol/sessions/{activityTimeId}/applicants", params = "status")
    public List<ApplicantDto> fetchApplicant(@PathVariable Long activityTimeId,
                                             @RequestParam("status") IsAuthorized isAuthorized,
                                             @PageableDefault Pageable pageable) {

        List<AppHistory> appHistories = volAppService.fetchApplicationsByCondition(
                activityTimeId,
                isAuthorized,
                pageable);

        return appHistories.stream().map(ApplicantDto::of).collect(Collectors.toList());
    }

    // 유저별 봉사 신청 내역 조회
    @GetMapping(value = "/member/application", params = "id")
    public List<AppHistoryDto> applicationsByMember(@RequestParam("id") Long memberId) {
        List<AppHistory> appHistories = volAppService.fetchApplications(memberId);
        return appHistories.stream().map(AppHistoryDto::of).collect(Collectors.toList());
    }

    // 사용자 승인 여부 변경
    @PutMapping("/member/application/{applicationId}/authorization")
    public AppHistoryDto authorizeApplicant(@PathVariable Long applicationId, @RequestBody AuthorizeForm authorizeForm) {
        AppHistory appHistory = volAppService.authorizeApplicant(applicationId, authorizeForm.getIsAuthorized());

        return AppHistoryDto.of(appHistory);
    }

    //    봉사자 정보 출력 API(PDF)


}
