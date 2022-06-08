package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.AppHistoryDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolAppForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.VolAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vol/apply")
@RequiredArgsConstructor
public class VolAppController {

    private final VolAppService volAppService;

    // 유저별 봉사 신청 내역 조회
    //    @GetMapping
    public List<AppHistoryDto> applicationsByMember(@RequestParam("member") Long memberId) {
        List<AppHistory> appHistories = volAppService.fetchApplications(memberId);
        return appHistories.stream().map(AppHistoryDto::of).collect(Collectors.toList());
    }

    // 봉사 신청 POST API
    @PostMapping()
    public String volApply(@RequestBody VolAppForm volAppForm) {
        volAppService.volApply(volAppForm);

        return "redirect:/";
    }

    //    신청 봉사자 정보 FORM GET API

    //    사용자 봉사 승인 POST API
    @PostMapping("/{id}/accept")
    public String acceptApplicant(@PathVariable("id") Long applicationId) {
        volAppService.acceptApplicant(applicationId);

        return "redirect:/";
    }

    //    사용자 봉사 거부 POST API
    @PostMapping("/{id}/deny")
    public String denyApplicant(@PathVariable("id") Long applicationId) {
        volAppService.denyApplicant(applicationId);

        return "redirect:/";
    }

    //    사용자 봉사 승인취소/거부취소 POST API
    @PostMapping("/{id}/pend")
    public String pendApplicant(@PathVariable("id") Long applicationId) {
        volAppService.pendApplicant(applicationId);

        return "redirect:/";
    }

    //    봉사자 정보 출력 API(PDF)


}
