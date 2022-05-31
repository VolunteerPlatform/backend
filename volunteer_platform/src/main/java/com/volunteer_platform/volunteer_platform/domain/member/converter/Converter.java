package com.volunteer_platform.volunteer_platform.domain.member.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Converter {

    @Data
    @AllArgsConstructor
    public static class MemberProfile1365<T> {
        private T profile1365;
    }

    @Data
    @AllArgsConstructor
    public static class MemberProfileInfo<T> {
        private T profileInfo;
    }

    @Data
    @AllArgsConstructor
    public static class MemberProfile<T> {
        private T profile;
    }

}
