package com.logging.aopmdc.api.time.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeRepositoryGWTTest {

    private final TimeRepository timeRepository = new TimeRepository();

    @Test
    @DisplayName("TimeZone InCorrectString -> Throw DateTimeException")
    void givenTimeZoneInCorrectString_WhenFindTimeByTimeZone_ThenThrowDateTimeException() {
        // Given
        String timeZone = "ASDFC";

        // When & Then
        assertThrows(DateTimeException.class,
                () -> timeRepository.findTimeByTimeZone(timeZone));
    }


    @Test
    @DisplayName("TimeZone CorrectString -> Return ZonedDateTime of given TimeZone")
    void givenTimeZoneCorrectString_WhenFindTimeByTimeZone_ThenReturnZonedDateTimeGivenTimeZone() {
        // Given
        String timeZone = "Africa/Abidjan";

        // When
        ZonedDateTime zonedDateTime = timeRepository.findTimeByTimeZone(timeZone);

        // Then
        Assertions.assertThat(zonedDateTime.getZone().getId())
                .isEqualTo(timeZone);
    }
}