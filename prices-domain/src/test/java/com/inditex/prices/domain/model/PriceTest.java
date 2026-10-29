package com.inditex.prices.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void keepsTheValuesItIsCreatedWith() {
        var startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        var endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        var price = new Price(1L, 35455L, 1, startDate, endDate, 0, new BigDecimal("35.50"), "EUR");

        assertThat(price.brandId()).isEqualTo(1L);
        assertThat(price.productId()).isEqualTo(35455L);
        assertThat(price.priceList()).isEqualTo(1);
        assertThat(price.startDate()).isEqualTo(startDate);
        assertThat(price.endDate()).isEqualTo(endDate);
        assertThat(price.priority()).isZero();
        assertThat(price.amount()).isEqualByComparingTo("35.50");
        assertThat(price.currency()).isEqualTo("EUR");
    }
}
