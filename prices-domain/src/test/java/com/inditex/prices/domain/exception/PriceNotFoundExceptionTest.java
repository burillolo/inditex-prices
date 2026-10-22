package com.inditex.prices.domain.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceNotFoundExceptionTest {

    @Test
    void describesTheSearchCriteria() {
        var exception = new PriceNotFoundException(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));

        assertThat(exception).hasMessage("No price found for brand 1 and product 35455 at 2020-06-14T10:00");
    }
}
