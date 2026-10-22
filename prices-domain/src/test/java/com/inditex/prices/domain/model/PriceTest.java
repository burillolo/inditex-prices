package com.inditex.prices.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void hasPrecedenceOverPriceWithLowerPriority() {
        assertThat(priceWithPriority(1).hasPrecedenceOver(priceWithPriority(0))).isTrue();
    }

    @Test
    void hasNoPrecedenceOverPriceWithHigherPriority() {
        assertThat(priceWithPriority(0).hasPrecedenceOver(priceWithPriority(1))).isFalse();
    }

    @Test
    void hasNoPrecedenceOverPriceWithSamePriority() {
        assertThat(priceWithPriority(1).hasPrecedenceOver(priceWithPriority(1))).isFalse();
    }

    private static Price priceWithPriority(int priority) {
        return new Price(1L, 35455L, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                priority, new BigDecimal("35.50"), "EUR");
    }
}
