package com.logging.aopmdc.api.time.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeDtoLombok {
    private String timeZone;
    private ZonedDateTime zonedDateTime;

    @Builder
    public TimeDtoLombok(String timeZone, ZonedDateTime zonedDateTime) {
        this.timeZone = timeZone;
        this.zonedDateTime = zonedDateTime;
    }
}
