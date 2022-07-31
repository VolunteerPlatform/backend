package com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype;

public enum Category {
    HOME_ENVIRONMENT("주거환경"),
    LIVING_SUPPORT("생활편의지원"),
    COUNSELING("상담"),
    EDUCATION("교육"),
    MEDICAL("보건의료"),
    RURAL("농어촌 봉사"),
    CULTURE("문화봉사"),
    ENVIRONMENT("환경보호"),
    ADMIN_ASSIST("행정보조"),
    SAFETY("안전예방"),
    PUBLIC("공익"),
    DISASTER("재해, 재난"),
    GLOBAL("해외봉사"),
    MENTOR("멘토링"),
    ETC("기타");

    private String name;

    Category(String name) {
        this.name = name;
    }
}
