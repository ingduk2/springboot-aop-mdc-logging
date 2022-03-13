package com.logging.aopmdc.api.time.repository;

import com.logging.aopmdc.common.aspect.log.annotation.LogExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Repository
public class TimeRepository {

    @LogExecutionTime
    public ZonedDateTime findTimeByTimeZone(String timeZone) {
        log.info("Repository timeZone : {}", timeZone);
        sleep(2000);

        return ZonedDateTime.now(ZoneId.of(timeZone));
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
