package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.AppHistoryDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.ApplicantDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.AuthorizeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.VolAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VolAppController {

    private final VolAppService volAppService;

    // 봉사 신청 POST API
    @PostMapping("/vol/sessions/{activitySessionId}")
    public DTOResponse<AppHistoryDto> volApply(@PathVariable Long activitySessionId, @RequestBody ApplicationForm applicationForm) {

        return new DTOResponse<>(volAppService.volApply(activitySessionId, applicationForm));
    }

    // 신청 봉사자 정보 GET API -> 쿼리 파라미터로 상태(PEND/ACCEPTED/DENY/FINISH 인지)
    @GetMapping(path = "/vol/sessions/{activitySessionId}/applicants", params = "status")
    public DTOResponse<List<ApplicantDto>> fetchApplicant(@PathVariable Long activitySessionId,
                                                          @RequestParam("status") IsAuthorized isAuthorized,
                                                          @PageableDefault Pageable pageable) {

        return new DTOResponse<>(volAppService.fetchApplicationsByCondition(activitySessionId, isAuthorized, pageable));
    }

    // 유저별 봉사 신청 내역 조회
    @GetMapping(value = "/members/application", params = "id")
    public DTOResponse<List<AppHistoryDto>> applicationsByMember(@RequestParam("id") Long memberId) {
        List<AppHistoryDto> appHistoryDtoList = volAppService.fetchApplications(memberId);

        return new DTOResponse<>(appHistoryDtoList);
    }

    // 사용자 승인 여부 변경
    @PutMapping("/members/application/{applicationId}/authorization")
    public DTOResponse<AppHistoryDto> authorizeApplicant(@PathVariable Long applicationId, @RequestBody AuthorizeForm authorizeForm) {

        return new DTOResponse<>(volAppService.authorizeApplicant(applicationId, authorizeForm.getIsAuthorized()));
    }

    // 사용자 신청 취소
    @DeleteMapping("/members/application/{applicationId}")
    public MessageResponse cancelApplication(@PathVariable Long applicationId) {
        volAppService.cancelApplication(applicationId);

        return MessageResponse.defaultOkayResponse();
    }

    //    봉사자 정보 출력 API(PDF)


}
