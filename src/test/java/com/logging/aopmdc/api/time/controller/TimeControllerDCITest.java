package com.logging.aopmdc.api.time.controller;

import com.logging.aopmdc.api.time.dto.TimeDto;
import com.logging.aopmdc.api.time.service.TimeService;
import com.logging.aopmdc.common.constant.ErrorCode;
import com.logging.aopmdc.common.constant.ErrorMessage;
import com.logging.aopmdc.common.exception.TimeZoneEmptyException;
import com.logging.aopmdc.common.exception.TimeZoneHasNotAlphaBetOrSlashException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TimeController Test")
@WebMvcTest(TimeController.class)
class TimeControllerDCITest {

    private final MockMvc mvc;

    @MockBean
    private TimeService timeService;

    public TimeControllerDCITest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Nested
    @DisplayName("ErrorResponse")
    class Describe_ErrorResponse {

        @Nested
        @DisplayName("timeService Throws TimeZoneHasNotAlphaBetOrSlashException")
        class Context_TimeZoneHasNotAlphaBetOrSlashException {

            @BeforeEach
            void mock() {
                given(timeService.findTimeByTimeZone(any()))
                        .willThrow(new TimeZoneHasNotAlphaBetOrSlashException());
            }

            @Test
            @DisplayName("ErrorResponse")
            void it_ErrorResponse() throws Exception {
                mvc.perform(get("/time?timezone="))
                        .andDo(print())
                        .andExpect(status().is5xxServerError())
                        .andExpect(jsonPath("$.success")
                                .value(false))
                        .andExpect(jsonPath("$.status")
                                .value(ErrorCode.INTERNAL_ERROR.getStatus()))
                        .andExpect(jsonPath("$.errorCode")
                                .value(ErrorCode.INTERNAL_ERROR.getCode()))
                        .andExpect(jsonPath("$.message")
                                .value(ErrorMessage.TIMEZONE_HAS_NOT_ALPHABET_OR_SLASH.getMessage()));
            }
        }

        @Nested
        @DisplayName("timeService Throws TimeZoneEmptyException")
        class Context_TimeZoneEmptyException {

            @BeforeEach
            void mock() {
                given(timeService.findTimeByTimeZone(any()))
                        .willThrow(new TimeZoneEmptyException());
            }

            @Test
            @DisplayName("ErrorResponse")
            void it_ErrorResponse() throws Exception {
                mvc.perform(get("/time?timezone="))
                        .andDo(print())
                        .andExpect(status().is5xxServerError())
                        .andExpect(jsonPath("$.success")
                                .value(false))
                        .andExpect(jsonPath("$.status")
                                .value(ErrorCode.INTERNAL_ERROR.getStatus()))
                        .andExpect(jsonPath("$.errorCode")
                                .value(ErrorCode.INTERNAL_ERROR.getCode()))
                        .andExpect(jsonPath("$.message")
                                .value(ErrorMessage.TIMEZONE_EMPTY.getMessage()));
            }
        }
    }

    @Nested
    @DisplayName("SuccessResponse")
    class Describe_SuccessResponse {

        @Nested
        @DisplayName("RequestPram TimeZone, timeService Return TimeDto")
        class Context_RequestParamTimeZone_TimeServiceReturnTimeDto {
            private String timeZone = "Asia/Seoul";

            @BeforeEach
            void mock() {
                given(timeService.findTimeByTimeZone(timeZone))
                        .willReturn(TimeDto.of(
                                timeZone,
                                ZonedDateTime.now(ZoneId.of(timeZone))
                        ));
            }

            @Test
            @DisplayName("SuccessResponse")
            void it_SuccessResponse() throws Exception {
                mvc.perform(get("/time?timezone=" + timeZone))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.success").value(true))
                        .andExpect(jsonPath("$.status").value(ErrorCode.OK.getStatus()))
                        .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                        .andExpect(jsonPath("$.data.timeZone").value("Asia/Seoul"));
            }
        }
    }

}