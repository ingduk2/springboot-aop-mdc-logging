package com.logging.aopmdc.api.time.controller;

import com.logging.aopmdc.api.response.ApiResponse;
import com.logging.aopmdc.api.time.dto.TimeDto;
import com.logging.aopmdc.api.time.service.TimeService;
import com.logging.aopmdc.common.aspect.log.annotation.LogFull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@Validated
@LogFull
@RestController
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/")
    public List<String> init() {
        return timeService.findTimezoneList();
    }

    @GetMapping("/time")
    public ApiResponse<TimeDto> findTime(
            @NotEmpty @RequestParam String timezone) {
        log.info("timezone : {}", timezone);
        return ApiResponse.of(
                timeService.findTimeByTimeZone(timezone)
        );
    }
}
