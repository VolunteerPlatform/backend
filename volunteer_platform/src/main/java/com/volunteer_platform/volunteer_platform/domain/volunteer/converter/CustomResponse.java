package com.volunteer_platform.volunteer_platform.domain.volunteer.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomResponse {
    @Getter
    @AllArgsConstructor
    public static class MessageResponse {
        int statusCode;
        String message;

        public static MessageResponse defaultOkayResponse() {
            return new MessageResponse(HttpStatus.OK.value(), "정상 처리 되었습니다.");
        }
    }

    @Getter
    @AllArgsConstructor
    public static class DTOResponse<T> {
        int statusCode;
        String message;
        T result;
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        int statusCode;
        String message;
    }
}
