package com.logging.aopmdc.api.time.service;

import com.logging.aopmdc.api.time.dto.TimeDto;
import com.logging.aopmdc.api.time.repository.TimeRepository;
import com.logging.aopmdc.common.exception.TimeZoneHasNotAlphaBetOrSlashException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("TimeServiceTest")
@ExtendWith(MockitoExtension.class)
class TimeServiceDCITest {

    @InjectMocks
    private TimeService timeService;
    @Mock
    private TimeRepository timeRepository;

    @Nested
    @DisplayName("fineTimeByTimeZoneMethod Test")
    class Describe_findTimeByTimeZoneMethod {

        @Nested
        @DisplayName("Param TimeZone Number String")
        class Context_Number_Timezone {
            String timeZone = "1234567";

            @Test
            @DisplayName("Throw TimeZoneHasNotAlphaBetOrSlashException")
            void it_Throw_TimeZoneHasNotAlphaBetOrSlashException() {
                assertThrows(TimeZoneHasNotAlphaBetOrSlashException.class,
                        () -> timeService.findTimeByTimeZone(timeZone));
            }
        }

        @Nested
        @DisplayName("Param TimeZone Correct String")
        class Context_Correct_TimeZone {
            String timeZone = "Asia/Seoul";
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));

            @BeforeEach
            void mock() {
                given(timeRepository.findTimeByTimeZone(timeZone))
                        .willReturn(zonedDateTime);
            }

            @Test
            @DisplayName("Return TimeDto")
            void it_Return_TimeDto() {
                TimeDto timeDto = timeService.findTimeByTimeZone(timeZone);

                assertThat(timeDto)
                        .hasFieldOrPropertyWithValue("timeZone", timeZone)
                        .hasFieldOrPropertyWithValue("zonedDateTime", zonedDateTime);

                assertThat(timeDto.timeZone())
                        .isEqualTo(timeZone);

                assertThat(zonedDateTime.getZone().getId())
                        .isEqualTo(timeZone);
            }
        }

    }
}