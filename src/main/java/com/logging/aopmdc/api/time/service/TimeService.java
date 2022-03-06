package com.logging.aopmdc.api.time.service;

import com.logging.aopmdc.api.time.dto.TimeDto;
import com.logging.aopmdc.api.time.repository.TimeRepository;
import com.logging.aopmdc.common.exception.TimeZoneEmptyException;
import com.logging.aopmdc.common.exception.TimeZoneHasNotAlphaBetOrSlashException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    public List<String> findTimezoneList() {
        return Arrays.stream(TimeZone.getAvailableIDs())
                .collect(Collectors.toList());
    }

    public TimeDto findTimeByTimeZone(String timeZone) {
        //Post 인 경우 Controller 에서 @Validated 잡는데
        //Get 일 때 String 1개 짜리는 모르겠어서 그냥 여기에 잡음.(Dto 주면 잡히나..?)
        if ( ! StringUtils.hasLength(timeZone))
            throw new TimeZoneEmptyException();

        if ( ! hasAlphaBetOrSlash(timeZone))
            throw new TimeZoneHasNotAlphaBetOrSlashException();

        log.info("Service, timeZone : {}", timeZone);
        return TimeDto.of(
                timeZone,
                timeRepository.findTimeByTimeZone(timeZone)
        );
    }

    private boolean hasAlphaBetOrSlash(String timeZone) {
        return Pattern.matches("^[a-zA-Z/]*$", timeZone);
    }

}
