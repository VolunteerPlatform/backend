package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.AppHistoryDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.ApplicantDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolAppForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.VolAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VolAppController {

    private final VolAppService volAppService;

    // 유저별 봉사 신청 내역 조회
    @GetMapping(value = "/vol/my-application", params = "member")
    public List<AppHistoryDto> applicationsByMember(@RequestParam("member") Long memberId) {
        List<AppHistory> appHistories = volAppService.fetchApplications(memberId);
        return appHistories.stream().map(AppHistoryDto::of).collect(Collectors.toList());
    }

    // 봉사 신청 POST API
    @PostMapping("/vol/act/{actId}/apply")
    public String volApply(@PathVariable("actId") Long activityId, @RequestBody VolAppForm volAppForm) {
        volAppService.volApply(activityId, volAppForm);

        return "redirect:/";
    }

    // 신청 봉사자 정보 GET API -> 쿼리 파라미터로 날짜랑 상태(PEND/ACCEPTED/DENY/FINISH 인지)
    @GetMapping(path = "/vol/act/{actId}/apply", params = {"date", "status"})
    public List<ApplicantDto> fetchApplicant(@PathVariable("actId") Long activityId,
                                             @RequestParam String date,
                                             @RequestParam String status,
                                             @PageableDefault Pageable pageable) {

        List<AppHistory> appHistories = volAppService.fetchApplicationsByCondition(
                activityId,
                LocalDate.parse(date),
                IsAuthorized.valueOf(status),
                pageable);

        return appHistories.stream().map(ApplicantDto::of).collect(Collectors.toList());
    }

    //    사용자 봉사 승인 POST API
    @PostMapping("/vol/act/{actId}/apply/{applicationId}/accept")
    public String acceptApplicant(@PathVariable Long applicationId) {
        volAppService.acceptApplicant(applicationId);

        return "redirect:/";
    }

    //    사용자 봉사 거부 POST API
    @PostMapping("/vol/act/{actId}/apply/{applicationId}/deny")
    public String denyApplicant(@PathVariable Long applicationId) {
        volAppService.denyApplicant(applicationId);

        return "redirect:/";
    }

    //    사용자 봉사 승인취소/거부취소 POST API
    @PostMapping("/vol/act/{actId}/apply/{applicationId}/pend")
    public String pendApplicant(@PathVariable Long applicationId) {
        volAppService.pendApplicant(applicationId);

        return "redirect:/";
    }

    //    봉사자 정보 출력 API(PDF)


}
