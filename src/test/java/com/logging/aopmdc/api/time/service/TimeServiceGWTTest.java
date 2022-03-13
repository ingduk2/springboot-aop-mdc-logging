package com.logging.aopmdc.api.time.service;

import com.logging.aopmdc.api.time.dto.TimeDto;
import com.logging.aopmdc.api.time.repository.TimeRepository;
import com.logging.aopmdc.common.exception.TimeZoneHasNotAlphaBetOrSlashException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TimeServiceGWTTest {

    @InjectMocks
    private TimeService timeService;
    @Mock
    private TimeRepository timeRepository;

    @Test
    @DisplayName("Throw TimeZoneHasNotAlphaBetOrSlashException")
    void givenNumberTimeZone_WhenFindTimeByTimeZone_ThenThrowTimeZoneHasNotAlphaBetOrSlashException() {
        // Given
        String timeZone = "1234567";

        // When & Then
        assertThrows(TimeZoneHasNotAlphaBetOrSlashException.class,
                () -> timeService.findTimeByTimeZone(timeZone));
    }

    @Test
    @DisplayName("Return TimeDto")
    void givenCorrectTimeZone_WhenFindTimeByTimeZone_ReturnTimeDto() {
        // Given
        String timeZone = "Asia/Seoul";
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));

        given(timeRepository.findTimeByTimeZone(timeZone))
                .willReturn(zonedDateTime);
        // When
        TimeDto timeDto = timeService.findTimeByTimeZone(timeZone);

        // Then
        assertThat(timeDto)
                .hasFieldOrPropertyWithValue("timeZone", timeZone)
                .hasFieldOrPropertyWithValue("zonedDateTime", zonedDateTime);

        assertThat(timeDto.timeZone())
                .isEqualTo(timeZone);

        assertThat(zonedDateTime.getZone().getId())
                .isEqualTo(timeZone);
    }


}