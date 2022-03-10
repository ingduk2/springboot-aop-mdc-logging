package com.logging.aopmdc.api.time.controller;

import com.logging.aopmdc.api.time.dto.TimeDto;
import com.logging.aopmdc.api.time.service.TimeService;
import com.logging.aopmdc.common.constant.ErrorCode;
import com.logging.aopmdc.common.constant.ErrorMessage;
import com.logging.aopmdc.common.exception.TimeZoneEmptyException;
import com.logging.aopmdc.common.exception.TimeZoneHasNotAlphaBetOrSlashException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TimeController.class)
class TimeControllerGWTTest {

    private final MockMvc mvc;

    @MockBean
    private TimeService timeService;

    public TimeControllerGWTTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @DisplayName("[API][GET] /time?timezone= ErrorResponse TimeZoneHasNotAlphaBetOrSlashException")
    void givenServiceThrowTimeZoneHasNotException_whenRequestTime_thenReturnErrorResponseByExceptionAdviceBaseException() throws Exception {
        // Given
        given(timeService.findTimeByTimeZone(any()))
                .willThrow(new TimeZoneHasNotAlphaBetOrSlashException());

        // When & Then
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

    @Test
    @DisplayName("[API][GET] /time?timezone= ErrorResponse TimeZoneEmptyException")
    void givenServiceThrowTimeZoneEmptyException_whenRequestTime_thenReturnErrorResponseByExceptionAdviceBaseException() throws Exception {
        // Given
        given(timeService.findTimeByTimeZone(any()))
                .willThrow(new TimeZoneEmptyException());

        // When & Then
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

    @Test
    @DisplayName("[API][GET] /time?timezone=Asia/Seoul 정상 동작")
    void givenTimeZone_whenRequestTime_thenReturnsTimeZoneDtoResponse() throws Exception {
        // Given
        String timeZone = "Asia/Seoul";

        given(timeService.findTimeByTimeZone(timeZone))
                .willReturn(TimeDto.of(
                        timeZone,
                        ZonedDateTime.now(ZoneId.of(timeZone))
                ));

        // When & Then
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