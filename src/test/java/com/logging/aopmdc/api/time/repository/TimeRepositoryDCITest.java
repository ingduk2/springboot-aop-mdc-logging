package com.logging.aopmdc.api.time.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TimeRepositoryTest")
class TimeRepositoryDCITest {

    private TimeRepository timeRepository = new TimeRepository();

    @Nested
    @DisplayName("findTimeByTimeZoneMethod Test")
    class Describe_findTimeByTimeZone {

        @Nested
        @DisplayName("InCorrect TimeZone String")
        class Context_InCorrect_TimeZone_String {
            String timeZone = "ASDFC";

            @Test
            @DisplayName("throw DateTimeException")
            void it_DateTimeException() {
                assertThrows(DateTimeException.class,
                        () -> timeRepository.findTimeByTimeZone(timeZone));
            }
        }

        @Nested
        @DisplayName("Correct TimeZone String")
        class Context_Correct_TimeZone_String {
            String timeZone = "Africa/Abidjan";

            @Test
            @DisplayName("Return ZonedDateTime given TimeZone")
            void it_Return_ZonedDateTime() {
                ZonedDateTime zonedDateTime = timeRepository.findTimeByTimeZone(timeZone);

                Assertions.assertThat(zonedDateTime.getZone().getId())
                        .isEqualTo(timeZone);
            }
        }
    }

}