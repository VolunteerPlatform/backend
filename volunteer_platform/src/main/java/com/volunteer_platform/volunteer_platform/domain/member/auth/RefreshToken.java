package com.volunteer_platform.volunteer_platform.domain.member.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(nullable = false)
    private String refreshToken;
}
