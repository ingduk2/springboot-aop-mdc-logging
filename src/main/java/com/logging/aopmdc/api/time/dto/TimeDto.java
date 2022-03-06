package com.logging.aopmdc.api.time.dto;

import java.time.ZonedDateTime;
public record TimeDto (
        String timeZone,
        ZonedDateTime zonedDateTime
){
    public static TimeDto of(
            String timeZone,
            ZonedDateTime zonedDateTime
    ) {
        return new TimeDto(timeZone, zonedDateTime);
    }
}
