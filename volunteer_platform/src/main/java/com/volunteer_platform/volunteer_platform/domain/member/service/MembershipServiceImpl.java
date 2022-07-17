package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Membership;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MembershipRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    @Override
    @Transactional
    public void createMembership(WithdrawalForm WithdrawalForm, Member member) {

        Membership membership = Membership.builder()
                .comment(WithdrawalForm.getComment())
                .member(member)
                .build();

        membershipRepository.save(membership);
    }
}
