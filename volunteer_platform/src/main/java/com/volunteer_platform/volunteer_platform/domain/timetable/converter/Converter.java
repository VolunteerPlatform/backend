package com.volunteer_platform.volunteer_platform.domain.timetable.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Converter {

    @Data
    @AllArgsConstructor
    public static class TimeTable<T> {
        private T timetable;
    }
}
