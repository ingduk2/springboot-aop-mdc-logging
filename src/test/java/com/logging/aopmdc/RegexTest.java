package com.logging.aopmdc;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class RegexTest {

    @Test
    void hasAlphaBetRegexTest() {
        assertThat(hasAlphabet("asdf")).isTrue();
        assertThat(hasAlphabet("1234")).isFalse();
        assertThat(hasAlphabet("Asia/Seoul")).isTrue();
    }

    private boolean hasAlphabet(String timeZone) {
        return Pattern.matches("^[a-zA-Z/]*$", timeZone);
    }
}
